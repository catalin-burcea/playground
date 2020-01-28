//package com.cburcea.playground.lombok;
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.experimental.Accessors;
//
//public class UserIntegrationTest {
//
//    public static void main(String[] args) {
//        User user = new User();
//        user.firstName("Test");
//        System.out.println(user);
//
//    }
//
//    @Getter
//    @Setter
//    @Accessors(fluent = true)
//    static class User {
//        private String firstName;
//    }
//}