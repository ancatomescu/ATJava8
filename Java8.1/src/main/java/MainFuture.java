import com.google.gson.Gson;
import futures.Comment;
import futures.Post;
import futures.Todo;
import futures.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MainFuture {
    public static void basicFeatures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableString = CompletableFuture.supplyAsync(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Something";
        }).exceptionally((e)->{
            return e.getMessage();
        });
        completableString.complete("Manual complete."); // override la ce am creat mai sus
        //System.out.println(completableString.get());

        CompletableFuture<Void> completableVoid = CompletableFuture.supplyAsync(()-> "Hello")
                .thenAccept(a-> System.out.println("Received: "+a));
        completableVoid.get();

        CompletableFuture<String> completableThenApply = CompletableFuture.supplyAsync(()->"Then apply")
                .thenApply(a->a.toUpperCase()).thenApply(a->{
                    String b = a.substring(0,1) + a.substring(1,2).toLowerCase() + a.substring(2);
                    return b;
                });
       // System.out.println(completableThenApply.get());
    }

    public static void gson() throws URISyntaxException, ExecutionException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = new URI("https://jsonplaceholder.typicode.com/posts/2");
        Gson gson = new Gson();
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).build();
        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    Post post = gson.fromJson(response.body(), Post.class);
                    //System.out.println(post);
                }).get();

        //System.out.println();

        URI uriPost = new URI("https://jsonplaceholder.typicode.com/posts");

        HttpRequest httpRequestPost = HttpRequest.newBuilder(uriPost).build();
        httpClient.sendAsync(httpRequestPost, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    List<Post> listPosts = Arrays.asList(gson.fromJson(response.body(), Post[].class));
                    return listPosts;
                })
                .thenAccept(System.out::println)
                        .get();

        httpClient.sendAsync(httpRequestPost, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    List<Post> listPosts = Arrays.asList(gson.fromJson(response.body(), Post[].class));
                    List<Integer> list = listPosts.stream()
                            .filter(post -> post.getUserId() == 3)
                            .map(post -> post.getUserId()).collect(Collectors.toList());
                    System.out.println(list);

                })
                .get();

        URI uriComments = new URI("https://jsonplaceholder.typicode.com/comments");
        HttpRequest httpRequestGetComments = HttpRequest.newBuilder(uriComments).build();
        httpClient.sendAsync(httpRequestGetComments,HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    List <Comment> listComments = Arrays.asList(gson.fromJson(response.body(), Comment[].class));
                    List <String> list = listComments.stream()
                            .filter(comment -> comment.getPostId() == 4)
                            .map(comment-> comment.getEmail()).collect(Collectors.toList());
                    System.out.println(list);
                })
                .get();
    }

    public static void thenCombineExample() throws URISyntaxException, ExecutionException, InterruptedException {
        Gson gson = new Gson();
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = new URI("https://jsonplaceholder.typicode.com/posts/4");
        HttpRequest httpRequestGetPost = HttpRequest.newBuilder(uri).build();
        CompletableFuture<String> completableFuturePostTitle = httpClient
                .sendAsync(httpRequestGetPost, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    Post post = gson.fromJson(response.body(), Post.class);
                    return post.getTitle();
                });

//        CompletableFuture<String> completableFuturePostUserName = httpClient
//                .sendAsync(httpRequestGetPost, HttpResponse.BodyHandlers.ofString())
//                .thenApply(response -> {
//                    List <Post> listPosts = Arrays.asList(gson.fromJson(response.body(), Post[].class));
//                    List name = listPosts.stream()
//                            .filter(post -> post.getId() == 4)
//                            .map(post-> post.getTitle()).collect(Collectors.toList());
//                });

        URI uriComments = new URI("https://jsonplaceholder.typicode.com/comments");
        HttpRequest httpRequestGetComments = HttpRequest.newBuilder(uriComments).build();
        CompletableFuture<List> completableFutureComment = httpClient
                .sendAsync(httpRequestGetComments, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    List <Comment> listComments = Arrays.asList(gson.fromJson(response.body(), Comment[].class));
                    List <String> list = listComments.stream()
                            .filter(comment -> comment.getPostId() == 4)
                            .map(comment-> comment.getName()).collect(Collectors.toList());
                    return list;
                });

        completableFuturePostTitle
                .thenCombine(completableFutureComment, (title, list) -> {
                    return "Title: " + title + " Comments: " + list;
                })
                .thenAccept(System.out::println)
                .get();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, URISyntaxException {
        basicFeatures();
        gson();
        thenCombineExample();
    }
}