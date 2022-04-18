package com.eldiar.rickandmorty.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eldiar.rickandmorty.data.models.Character
import com.eldiar.rickandmorty.data.network.CharactersApi
import com.eldiar.rickandmorty.utils.Constants.PAGE_SIZE
import com.eldiar.rickandmorty.utils.Constants.START_PAGE
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingSource(private val api: CharactersApi) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: START_PAGE
        return try {
            val response = api.getAllCharacters(pageIndex)
            val data = response.results

            val nextKey = if (data.isEmpty()) {
                null
            } else {
                pageIndex + (params.loadSize / PAGE_SIZE)
            }
            LoadResult.Page(
                data = data,
                prevKey = if (pageIndex == START_PAGE) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}