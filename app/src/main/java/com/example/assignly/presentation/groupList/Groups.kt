package com.example.assignly.presentation.groupList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.assignly.R
import com.example.assignly.api.models.Group
import com.example.assignly.presentation.Navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    navController: NavController,
    viewModel: GroupViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    viewModel.fetchGroups(viewModel.token.toString())
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { query ->
                    searchQuery = query
                    if (searchQuery.isNotEmpty()) {
                        viewModel.searchGroups(searchQuery)
                    } else {
                        viewModel.fetchGroups(viewModel.token.toString())
                    }
                },
                placeholder = { Text("Search Groups") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                onSearch = {
//                    if (searchQuery.isNotEmpty()) {
//                        viewModel.searchGroups(searchQuery)
//                    }
                },
                active = searchQuery.isNotEmpty(),
                onActiveChange = { active ->
//                    if (!active) {
//                        viewModel.fetchGroups(viewModel.token.toString())
//                    }
                },
                content = {
                    if (uiState is GroupUiState.Search) {
                        val searchState = uiState as GroupUiState.Search
                        if (searchState.searchedGroups.isEmpty()) {
                            Text(
                                text = "Nothing found",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelLarge,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        } else {
                            GroupList(navController, groups = searchState.searchedGroups)
                        }
                    }
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

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
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                else -> Unit
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.plus),
                contentDescription = "Add group",
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.End)
                    .padding(end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .clickable { navController.navigate(Navigation.ADD_GROUP.toString()) }
            )
        }
    }
}

@Composable
fun GroupList(navController: NavController, groups: List<Group>) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(groups.size) { index ->
            GroupCard(navController, groups[index])
        }
    }
}

@Composable
fun GroupCard(navController: NavController, group: Group) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("${Navigation.TASK_LIST}/${group.id}") }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(group.image),
                contentDescription = group.name,
                modifier = Modifier
                    .size(70.dp)
            )
            Text(
                text = group.name,
                fontSize = 20.sp,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

    }
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}
