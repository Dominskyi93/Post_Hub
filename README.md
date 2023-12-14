# PostHub

PostHub is a mobile application that allows users to create and manage posts.
The app consists of four screens: Login, Registration, Post List, and Post Editing.

## Features

### Login Screen

- Authentication using email and password. (Firebase)
- Remember me function.

![Authorization_screen.png](readme_photos%2FAuthorization_screen.png)

### Registration Screen

- Email and password validation.
  The password must contain at least one uppercase and lowercase letter,
  a number, and a special character (#?!@$%^&*-). The password must be at least 6 characters long
- Double input password

![Registration_screen.png](readme_photos%2FRegistration_screen.png)
### Post List Screen

- Display a list of posts.
- Each post includes an image, comment, color and creation or last edit date.
- Background color of the comment is taken from the post characteristics.
- Clicking on a post navigates to the Post Editing screen.
- Logout option in the toolbar with popup menu.
- FAB button to add new post.

![Home_page_screen.png](readme_photos%2FHome_page_screen.png)

### Post Editing Screen

- Create a new post or edit an existing one.
- Prompt user with a notification if they try to leave without saving changes.
- Posts consist of a comment, creation date, last edit date (nullable), image (URL), and color.
- Use a dropdown menu for selecting colors.
- Use a horizontal list for selecting images.

![Editing_screen.png](readme_photos%2FEditing_screen.png )

## Technical Details

- Architecture: MVVM (Model-View-ViewModel).
- Dependency Injection: Hilt/Dagger.
- Asynchronous Programming: LiveData, Flow, Coroutines.
- Data Persistence: Room.
- Network Requests: Retrofit.
- UI: XML.

## Exception Handling

Handle exceptions gracefully, such as when there's no internet connection to load images. Use SnackBar to inform the user about the issue.


## Building and Running the App

1. Clone the repository:

   ```bash
   git clone https://github.com/Dominskyi93/Post_Hub.git
