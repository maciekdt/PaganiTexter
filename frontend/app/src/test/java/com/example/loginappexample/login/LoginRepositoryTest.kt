package com.example.loginappexample.login

import com.example.loginappexample.data.login.*
import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.service.exceptions.NotAuthorizedException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertFailsWith

class LoginRepositoryTest {


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_whenCorrectLoginDataAndServiceWorks_thenLoginCorrectly(){
        val correctUsername = "user"
        val correctPassword = "pass"
        val correctResult = LoggedInUser("someToken", "someId")

        val remoteDataSource = mockk<LoginRemoteDataSource>()
        coEvery {remoteDataSource.login(correctUsername, correctPassword)} returns correctResult
        val localDataSource = mockk<LoginLocalDataSource>()
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            val result = loginRepository.login(correctUsername, correctPassword, false)
            Assert.assertEquals(correctResult, result)
            Assert.assertEquals(correctResult, loginRepository.getLoggedInUser())
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_whenIncorrectLoginDataAndServiceWorks_thenRejectLogin(){
        val incorrectUsername = "user"
        val incorrectPassword = "pass"

        val remoteDataSource = mockk<LoginRemoteDataSource>()
        coEvery {remoteDataSource.login(any(),any())} throws NotAuthorizedException("Test")
        val localDataSource = mockk<LoginLocalDataSource>()
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            assertFailsWith<NotAuthorizedException>{
                loginRepository.login("any", "any", false)}
            Assert.assertEquals(null, loginRepository.getLoggedInUser())
        }
    }

}