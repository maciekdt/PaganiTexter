package com.example.loginappexample.login

import com.example.loginappexample.data.login.*
import com.example.loginappexample.data.model.LoggedInUser
import com.example.loginappexample.data.model.LoginData
import com.example.loginappexample.service.exceptions.InternalServerException
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
    fun loginByRemoteDataSource_whenCorrectLoginDataAndServiceWorks_thenLoginCorrectly(){
        val correctLoginData = LoginData("user", "pass")
        val correctResult = LoggedInUser("someToken", "someId")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.login(correctLoginData)} returns correctResult
        val localDataSource = mockk<ILoginLocalDataSource>()
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            val result = loginRepository.loginByRemoteDataSource(correctLoginData, false)
            Assert.assertEquals(correctResult, result)
            Assert.assertEquals(correctResult, loginRepository.getLoggedInUser())
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByRemoteDataSource_whenIncorrectLoginDataAndServiceWorks_thenThrowException(){
        val incorrectLoginData = LoginData("user", "pass")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.login(incorrectLoginData)} throws NotAuthorizedException("Test")
        val localDataSource = mockk<ILoginLocalDataSource>()
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            assertFailsWith<NotAuthorizedException>{
                loginRepository.loginByRemoteDataSource(incorrectLoginData, false)}
            Assert.assertEquals(null, loginRepository.getLoggedInUser())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByRemoteDataSource_whenServiceError_thenThrowException(){
        val loginData = LoginData("user", "pass")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.login(any())} throws InternalServerException("Test")
        val localDataSource = mockk<ILoginLocalDataSource>()
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            assertFailsWith<InternalServerException>{
                loginRepository.loginByRemoteDataSource(loginData, false)}
            Assert.assertEquals(null, loginRepository.getLoggedInUser())
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByLocalDataSource_whenCorrectDataInCache_thenLoginCorrectly(){
        val correctLoggedInUser = LoggedInUser("token", "id")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.checkToken(correctLoggedInUser.token)} returns true

        val localDataSource = mockk<ILoginLocalDataSource>()
        coEvery {localDataSource.login()} returns correctLoggedInUser
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            val result = loginRepository.loginByLocalDataSource()
            Assert.assertTrue(result)
            Assert.assertEquals(correctLoggedInUser, loginRepository.getLoggedInUser())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByLocalDataSource_whenIncorrectDataInCache_thenRejectLogin(){
        val incorrectLoggedInUser = LoggedInUser("token", "id")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.checkToken(incorrectLoggedInUser.token)} returns false

        val localDataSource = mockk<ILoginLocalDataSource>()
        coEvery {localDataSource.login()} returns incorrectLoggedInUser
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            val result = loginRepository.loginByLocalDataSource()
            Assert.assertFalse(result)
            Assert.assertEquals(null, loginRepository.getLoggedInUser())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByLocalDataSource_whenEmptyCache_thenRejectLogin(){

        val remoteDataSource = mockk<ILoginRemoteDataSource>()

        val localDataSource = mockk<ILoginLocalDataSource>()
        coEvery {localDataSource.login()} returns null
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            val result = loginRepository.loginByLocalDataSource()
            Assert.assertFalse(result)
            Assert.assertEquals(null, loginRepository.getLoggedInUser())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun loginByLocalDataSource_whenServiceError_thenThrowException(){
        val correctLoggedInUser = LoggedInUser("token", "id")

        val remoteDataSource = mockk<ILoginRemoteDataSource>()
        coEvery {remoteDataSource.checkToken(any())} throws InternalServerException("Test")

        val localDataSource = mockk<ILoginLocalDataSource>()
        coEvery {localDataSource.login()} returns correctLoggedInUser
        val loginRepository = LoginRepository(remoteDataSource, localDataSource)

        runTest{
            assertFailsWith<InternalServerException> {
                loginRepository.loginByLocalDataSource()
            }
        }
    }

}