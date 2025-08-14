# Testing Guide

## Overview
This project includes comprehensive testing with different approaches for different test types.

## Test Types

### 1. Unit Tests (`src/test/`)
- **Location**: `app/src/test/`
- **Purpose**: Test individual components in isolation
- **Mocking**: **YES** - Use mocks for dependencies
- **Examples**: `PlayersViewModelTest`, `FollowUseCasesTest`

### 2. UI Tests (`src/androidTest/`)
- **Location**: `app/src/androidTest/`
- **Purpose**: Test UI components and user interactions
- **Mocking**: **DEPENDS ON APPROACH**

## UI Testing Approaches

### Approach 1: Compose Rule with Mocking (`PlayersScreenTest.kt`)
- **Use Case**: Testing individual UI components in isolation
- **Mocking**: **YES** - Mock ViewModel and provide realistic test data
- **Pros**: Fast, isolated, predictable
- **Cons**: Requires careful mock setup, may miss integration issues

```kotlin
@Test
fun testPlayersScreen_DisplaysPlayerRow() {
    val mockViewModel = createMockViewModelWithData()
    composeTestRule.setContent {
        PlayersScreen(viewModel = mockViewModel, showOnlyFollowed = false)
    }
    composeTestRule.onNodeWithText("Test Player").assertExists()
}
```

### Approach 2: Android Compose Rule with Hilt (`PlayersFlowTest.kt`)
- **Use Case**: Testing full app integration and user flows
- **Mocking**: **NO** - Use test modules to provide test data
- **Pros**: Tests real integration, catches real issues
- **Cons**: Slower, requires more setup

```kotlin
@HiltAndroidTest
class PlayersFlowTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    @Test
    fun testMainFlow_DisplayPlayersAndNavigate() {
        composeTestRule.waitForAppToLoad()
        composeTestRule.onNodeWithText("Test Player").assertExists()
    }
}
```

## When to Use Each Approach

### Use Compose Rule + Mocking When:
- Testing individual UI components
- Need fast test execution
- Testing specific UI behavior without full app context
- Unit testing UI logic

### Use Android Compose Rule + Hilt When:
- Testing complete user flows
- Testing navigation between screens
- Testing real data integration
- End-to-end testing scenarios

## Mocking Strategy for UI Tests

### If Using Mocks:
1. **Provide Realistic Data**: Mock ViewModel must return data that UI can actually render
2. **Mock StateFlows**: Return `MutableStateFlow` with test data
3. **Mock Functions**: Implement realistic behavior for user interactions

```kotlin
private fun createMockViewModelWithData(): PlayersViewModel {
    val mockViewModel = mock(PlayersViewModel::class.java)
    
    // Create test data
    val testPlayer = Player(name = "Test Player", ...)
    val testPagingData = PagingData.from(listOf(testPlayer))
    
    // Mock StateFlows with real data
    val pagedPlayersFlow = MutableStateFlow(testPagingData)
    `when`(mockViewModel.pagedPlayers).thenReturn(pagedPlayersFlow)
    
    // Mock functions with realistic behavior
    `when`(mockViewModel.onToggleFollow(any())).thenAnswer { invocation ->
        // Implement realistic follow/unfollow logic
    }
    
    return mockViewModel
}
```

### If Using Test Modules:
1. **Create Test Implementations**: Override repositories with test data
2. **Use Hilt Test Modules**: Replace production dependencies
3. **Provide Consistent Test Data**: Use the same test data across tests

```kotlin
@Module
@TestInstallIn(components = [SingletonComponent::class])
object TestDataModule {
    @Provides
    fun provideTestPlayersRepository(): PlayersRepository {
        return object : PlayersRepository {
            override suspend fun getPlayersPaged(...): Flow<Player> {
                return flow { emit(TestData.testPlayer) }
            }
        }
    }
}
```

## Test Data Management

### Centralized Test Data
```kotlin
object TestData {
    val testPlayer = Player(
        name = "Test Player",
        team = Team(name = "Test Team", rank = 1),
        league = League(name = "Test League", country = "Test Country", rank = 1, totalMatches = 10),
        totalGoal = 15
    )
    
    val testPlayers = listOf(testPlayer, testPlayer2)
}
```

## Best Practices

1. **Choose the Right Approach**: Match testing strategy to what you're testing
2. **Consistent Test Data**: Use the same test data across related tests
3. **Realistic Mocks**: If mocking, provide data that UI can actually render
4. **Test User Flows**: Focus on testing what users actually do
5. **Avoid Over-Mocking**: Don't mock things that don't need mocking

## Common Issues and Solutions

### Issue: "Mock not found in UI tests"
**Solution**: You don't need mocks for UI tests using `createAndroidComposeRule<MainActivity>()`. Use test modules instead.

### Issue: UI not rendering with mocked ViewModel
**Solution**: Ensure your mock ViewModel returns realistic data that matches what the UI expects.

### Issue: Tests failing due to missing dependencies
**Solution**: Use `@HiltAndroidTest` and proper test modules for integration testing.

## Running Tests

```bash
# Unit tests
./gradlew test

# UI tests
./gradlew connectedAndroidTest

# Specific test class
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=ir.miare.androidcodechallenge.PlayersFlowTest
```
