package com.example.jetshoes.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetshoes.CategoryRow
import com.example.jetshoes.R
import com.example.jetshoes.di.Injection
import com.example.jetshoes.ui.ViewModelFactory
import com.example.jetshoes.ui.components.RewardItem
import com.example.jetshoes.ui.components.SectionText
import com.example.jetshoes.ui.theme.JetShoesTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val scope = rememberCoroutineScope()

    val listState = rememberLazyGridState()

    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    val groupedHeroes by viewModel.groupedHeroes.collectAsState()
    val query by viewModel.query

    Box (modifier = modifier){
        Column {
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
            if (query.isEmpty()){
                SectionText(stringResource(R.string.section_category))
                CategoryRow()
            }

            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .testTag("RewardList")
                    .padding(top = 20.dp)
                    .fillMaxWidth()
            ){
                groupedHeroes.forEach { (initial, heroes) ->
                    items(heroes, key = { it.id }) { hero ->
                        RewardItem(
                            image = hero.image,
                            title = hero.title,
                            requiredPoint = hero.requiredPoint,
                            modifier = modifier
                                .clickable {
                                    navigateToDetail(hero.id)
                                }
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100))
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically (),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 20.dp, start = 330.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {scope.launch {
                listState.animateScrollToItem(index = 0)
            }})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_hero))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    FilledIconButton(onClick = onClick,
        modifier = modifier) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun showButtonTop(){
    JetShoesTheme {
        ScrollToTopButton(onClick = { /*TODO*/ })
    }
}