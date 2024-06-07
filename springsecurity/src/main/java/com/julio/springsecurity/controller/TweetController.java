package com.julio.springsecurity.controller;

import com.julio.springsecurity.controller.dto.FeedDto;
import com.julio.springsecurity.controller.dto.FeedItemDto;
import com.julio.springsecurity.controller.dto.TweetDto;
import com.julio.springsecurity.entities.Role;
import com.julio.springsecurity.entities.Tweet;
import com.julio.springsecurity.repository.TweetRepository;
import com.julio.springsecurity.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tweets = tweetRepository.findAll(
                        PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(tweet ->
                        new FeedItemDto(
                                tweet.getTweetId(),
                                tweet.getContent(),
                                tweet.getUser().getUsername())
                );

        return ResponseEntity.ok(new FeedDto(
                tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody TweetDto tweetDto, JwtAuthenticationToken authentication) {

        var user = userRepository.findById(UUID.fromString(authentication.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(tweetDto.content());

        tweetRepository.save(tweet);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id, JwtAuthenticationToken authentication) {

        var user = userRepository.findById(UUID.fromString(authentication.getName()));


        var tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles().stream().anyMatch(role -> role.getName().equals(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getId().equals(UUID.fromString(authentication.getName()))) {
            tweetRepository.delete(tweet);
        }else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok().build();
    }

}
