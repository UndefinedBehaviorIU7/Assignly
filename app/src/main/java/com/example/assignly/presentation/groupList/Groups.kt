package com.example.assignly.presentation.groupList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignly.api.models.Group
import com.example.assignly.presentation.Navigation
import com.example.assignly.presentation.taskList.TasksList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    navController: NavController,
    viewModel: GroupViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    viewModel.fetchGroups(viewModel.token.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { query ->
                searchQuery = query
            },
            placeholder = { Text("Search Groups") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier.fillMaxWidth(),
            onSearch = {
                viewModel.searchGroups(searchQuery)
            },
            active = searchQuery.isNotEmpty(),
            onActiveChange = { _ -> /* Логика активации/деактивации поиска */ },
            content = {
                if (searchQuery.isEmpty()) {
                    Text("Enter group name to search", style = TextStyle(fontStyle = FontStyle.Italic))
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (uiState) {
            is GroupUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is GroupUiState.All -> {
                GroupList(navController, groups = (uiState as GroupUiState.All).groups)
            }
            is GroupUiState.Error -> {
                Text(
                    text = (uiState as GroupUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> Unit
        }

        FloatingActionButton(
            onClick = { navController.navigate(Navigation.ADD_GROUP.toString()) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.End)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Group")
        }
    }
}

@Composable
fun GroupList(navController: NavController, groups: List<Group>) {
    LazyColumn(
        modifier = Modifier
    ) {
        items(groups.size) { index ->
            GroupCard(navController, groups[index])
        }
    }
}

@Composable
fun GroupCard(navController: NavController, group: Group) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = { navController.navigate("${Navigation.TASK_LIST}/${group.id}") }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(group.image),
                contentDescription = group.name,
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = group.name,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
