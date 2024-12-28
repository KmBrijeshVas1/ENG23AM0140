import java.util.*;

// Represents a Post in the system
class Post {
    private static int postCounter = 0;
    private int postId;
    private String content;
    private User author;
    private List<String> comments;
    private Set<User> likes;

    public Post(String content, User author) {
        this.postId = ++postCounter;
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
        this.likes = new HashSet<>();
    }

    public int getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void like(User user) {
        likes.add(user);
    }

    public void showPost() {
        System.out.println("\nPost ID: " + postId);
        System.out.println("Content: " + content);
        System.out.println("Author: " + author.getUsername());
        System.out.println("Likes: " + likes.size());
        if (comments.isEmpty()) {
            System.out.println("No comments yet.");
        } else {
            System.out.println("Comments:");
            for (String comment : comments) {
                System.out.println("- " + comment);
            }
        }
    }
}

// Represents a Music track in the system
class Music {
    private static int musicCounter = 0;
    private int musicId;
    private String title;
    private String artist;
    private User uploader;

    public Music(String title, String artist, User uploader) {
        this.musicId = ++musicCounter;
        this.title = title;
        this.artist = artist;
        this.uploader = uploader;
    }

    public int getMusicId() {
        return musicId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public User getUploader() {
        return uploader;
    }

    public void playMusic() {
        System.out.println("\nNow playing: " + title + " by " + artist);
    }
}

// Represents a Story in the system
class Story {
    private static int storyCounter = 0;
    private int storyId;
    private String content;
    private User author;
    private Date uploadTime;

    public Story(String content, User author) {
        this.storyId = ++storyCounter;
        this.content = content;
        this.author = author;
        this.uploadTime = new Date();
    }

    public int getStoryId() {
        return storyId;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public boolean isExpired() {
        // Stories expire after 24 hours (for simplicity)
        return (new Date().getTime() - uploadTime.getTime()) > 86400000;
    }

    public void showStory() {
        System.out.println("\nStory ID: " + storyId);
        System.out.println("Content: " + content);
        System.out.println("Uploaded by: " + author.getUsername());
        System.out.println("Story expires in: " + (24 - (new Date().getTime() - uploadTime.getTime()) / 3600000) + " hours");
    }
}

// Represents a User in the system
class User {
    private String username;
    private String password;
    private Set<User> following;
    private List<Post> posts;
    private List<Music> musicUploads;
    private List<Story> stories;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.following = new HashSet<>();
        this.posts = new ArrayList<>();
        this.musicUploads = new ArrayList<>();
        this.stories = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<Music> getMusicUploads() {
        return musicUploads;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void createPost(String content) {
        Post newPost = new Post(content, this);
        posts.add(newPost);
    }

    public void uploadMusic(String title, String artist) {
        Music newMusic = new Music(title, artist, this);
        musicUploads.add(newMusic);
        System.out.println("Music uploaded: " + title + " by " + artist);
    }

    public void uploadStory(String content) {
        Story newStory = new Story(content, this);
        stories.add(newStory);
        System.out.println("Story uploaded: " + content);
    }

    public void follow(User user) {
        if (!this.equals(user)) {
            following.add(user);
            System.out.println("Now following " + user.getUsername());
        } else {
            System.out.println("Cannot follow yourself.");
        }
    }

    public void unfollow(User user) {
        if (following.remove(user)) {
            System.out.println("Unfollowed " + user.getUsername());
        } else {
            System.out.println("You are not following " + user.getUsername());
        }
    }

    public void likePost(Post post) {
        post.like(this);
        System.out.println("Liked post from " + post.getAuthor().getUsername());
    }

    public void commentOnPost(Post post, String comment) {
        post.addComment(comment);
        System.out.println("Commented on post from " + post.getAuthor().getUsername());
    }

    public void viewFeed() {
        if (following.isEmpty()) {
            System.out.println("You are not following anyone yet.");
        } else {
            System.out.println("\nYour Feed:");
            for (User user : following) {
                for (Post post : user.getPosts()) {
                    post.showPost();
                }
            }
        }
    }

    public void viewStories() {
        if (stories.isEmpty()) {
            System.out.println("No stories to view.");
        } else {
            for (Story story : stories) {
                if (!story.isExpired()) {
                    story.showStory();
                } else {
                    System.out.println("This story has expired.");
                }
            }
        }
    }

    public void listenToMusic() {
        if (musicUploads.isEmpty()) {
            System.out.println("No music uploads.");
        } else {
            for (Music music : musicUploads) {
                music.playMusic();
            }
        }
    }
}

// Handles user registration, login, and management
class UserSystem {
    private Map<String, User> users;
    private User currentUser;

    public UserSystem() {
        users = new HashMap<>();
        currentUser = null;
    }

    public void register(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else {
            User newUser = new User(username, password);
            users.put(username, newUser);
            System.out.println("Registration successful!");
        }
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            System.out.println("\nList of users:");
            for (String username : users.keySet()) {
                System.out.println("- " + username);
            }
        }
    }
}

public class SocialMediaApp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserSystem userSystem = new UserSystem();

    public static void main(String[] args) {
        while (true) {
            mainMenu();
        }
    }

    private static void mainMenu() {
        System.out.println("\nWelcome to SocialMediaApp");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                System.out.println("Goodbye!");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        userSystem.register(username, password);
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userSystem.login(username, password)) {
            System.out.println("Login successful!");
            loggedInMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void loggedInMenu() {
        while (userSystem.isLoggedIn()) {
            User currentUser = userSystem.getCurrentUser();
            System.out.println("\nLogged in as " + currentUser.getUsername());
            System.out.println("1. Create Post");
            System.out.println("2. View My Posts");
            System.out.println("3. View Feed");
            System.out.println("4. Follow a User");
            System.out.println("5. Unfollow a User");
            System.out.println("6. Like a Post");
            System.out.println("7. Comment on a Post");
            System.out.println("8. Upload Music");
            System.out.println("9. Listen to Music");
            System.out.println("10. Upload Story");
            System.out.println("11. View Stories");
            System.out.println("12. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createPost();
                    break;
                case 2:
                    viewMyPosts();
                    break;
                case 3:
                    currentUser.viewFeed();
                    break;
                case 4:
                    followUser();
                    break;
                case 5:
                    unfollowUser();
                    break;
                case 6:
                    likePost();
                    break;
                case 7:
                    commentOnPost();
                    break;
                case 8:
                    uploadMusic();
                    break;
                case 9:
                    currentUser.listenToMusic();
                    break;
                case 10:
                    uploadStory();
                    break;
                case 11:
                    currentUser.viewStories();
                    break;
                case 12:
                    userSystem.logout();
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createPost() {
        System.out.print("Enter post content: ");
        String content = scanner.nextLine();
        userSystem.getCurrentUser().createPost(content);
    }

    private static void viewMyPosts() {
        User currentUser = userSystem.getCurrentUser();
        if (currentUser.getPosts().isEmpty()) {
            System.out.println("You have no posts.");
        } else {
            for (Post post : currentUser.getPosts()) {
                post.showPost();
            }
        }
    }

    private static void followUser() {
        userSystem.displayUsers();
        System.out.print("Enter username to follow: ");
        String username = scanner.nextLine();
        User userToFollow = userSystem.getUsers().get(username);

        if (userToFollow != null) {
            userSystem.getCurrentUser().follow(userToFollow);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void unfollowUser() {
        System.out.print("Enter username to unfollow: ");
        String username = scanner.nextLine();
        User userToUnfollow = userSystem.getUsers().get(username);
        if (userToUnfollow != null) {
            userSystem.getCurrentUser().unfollow(userToUnfollow);
        } else {
            System.out.println("User not found.");
        }
    }

    private static void likePost() {
        System.out.print("Enter Post ID to like: ");
        int postId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (Post post : userSystem.getCurrentUser().getPosts()) {
            if (post.getPostId() == postId) {
                userSystem.getCurrentUser().likePost(post);
                return;
            }
        }
        System.out.println("Post not found.");
    }

    private static void commentOnPost() {
        System.out.print("Enter Post ID to comment on: ");
        int postId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        for (Post post : userSystem.getCurrentUser().getPosts()) {
            if (post.getPostId() == postId) {
                System.out.print("Enter your comment: ");
                String comment = scanner.nextLine();
                userSystem.getCurrentUser().commentOnPost(post, comment);
                return;
            }
        }
        System.out.println("Post not found.");
    }

    private static void uploadMusic() {
        System.out.print("Enter music title: ");
        String title = scanner.nextLine();
        System.out.print("Enter artist name: ");
        String artist = scanner.nextLine();
        userSystem.getCurrentUser().uploadMusic(title, artist);
    }

    private static void uploadStory() {
        System.out.print("Enter story content: ");
        String content = scanner.nextLine();
        userSystem.getCurrentUser().uploadStory(content);
    }
}
