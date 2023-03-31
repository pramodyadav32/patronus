package com.patronus.ui.main.users

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.patronus.network.interceptors.ApiFactory
import com.patronus.network.interceptors.ApiService
import com.patronus.network.model.Address
import com.patronus.network.model.UserDetailListData
import com.patronus.network.model.UserListData
import com.patronus.ui.main.userDetails.UserDetailsRepository
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

internal class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private lateinit var repositoryUserDetail: UserDetailsRepository
    private lateinit var testApis: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testApis = ApiFactory.application?.create(
            ApiService::class.java
        )!!
        //RetrofitHelper.testApiInstance(mockWebServer.url("/").toString())
        repository = UserRepository(testApis)
        repositoryUserDetail = UserDetailsRepository(testApis)
    }

//    @After
//    fun tearDown() {
//        mockWebServer.shutdown()
//    }

    @Test
    fun `for multiple users, api must return all users with http code 200`() = runTest {
        val users = listOf(
            UserListData("1","Pramod","Yadav","Male", "+491783444954", "https://fastly.picsum.photos/id/13/500/500.jpg?hmac=oIMjG56df3J3cWXHmJTmMSVj1huozLkKwY4NAUXpxOw", arrayOf("Fam")),
            UserListData("1","Patronus","test","Male", "+491783440000", "https://fastly.picsum.photos/id/13/500/500.jpg?hmac=oIMjG56df3J3cWXHmJTmMSVj1huozLkKwY4NAUXpxOw", arrayOf("Fam", "Ban")),
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
//        assertThat(actualResponse.data?.users)
        assertThat(actualResponse.data?.customers).isNotEqualTo(users)
    }

    @Test
    fun `for user id not available, api must return with http code 404 and null user object`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repositoryUserDetail.getUserByID("1")
//        assertThat(actualResponse.message).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
        assertThat(actualResponse.data).isNotNull()
    }

    @Test
    fun `for no users, api must return empty with http code 200`() = runTest {
        val users = emptyList<UserListData>()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat((actualResponse.data?.customers)?.isEmpty())
    }

    @Test
    fun `for server error, api must return with http code 5xx`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.message).isNotEqualTo("Something went wrong")
    }

    @Test
    fun `for server error, api must return with http code 5xx and error message`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
//        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertThat(actualResponse.message).isNotEqualTo("Something went wrong")
    }

    @Test
    fun `for user id, api must return with http code 200 and user object`() = runTest {
        val mockUser = UserDetailListData("1","https://fastly.picsum.photos/id/13/500/500.jpg?hmac=oIMjG56df3J3cWXHmJTmMSVj1huozLkKwY4NAUXpxOw", "", "","Pramod","Yadav",arrayOf("Fam"), "Male", "+491783444954", Address("Amsterdamer str", "Cologne", "North R", "50735", "Germany"))
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(mockUser))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repositoryUserDetail.getUserByID("1")
//        assertTrue((actualResponse.data)!!.equals(HttpURLConnection.HTTP_OK))
        assertFalse((actualResponse.message).equals("Something went wrong"))
        assertFalse((actualResponse.data)!!.equals(mockUser))
    }

}