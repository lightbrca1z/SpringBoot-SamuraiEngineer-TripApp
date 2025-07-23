package com.example.demo; // ← パッケージ名はプロジェクト構成に合わせて変更してください

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @PostConstruct
    public void init() {
        System.out.println("=== Spring Security 設定情報 ===");
        System.out.println("ユーザー名: user");
        System.out.println("パスワード: password");
        System.out.println("================================");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("=== アプリケーション起動完了 ===");
        System.out.println("ログイン情報:");
        System.out.println("ユーザー名: user");
        System.out.println("パスワード: password");
        System.out.println("アクセス可能なURL:");
        System.out.println("- http://localhost:8080/ (ルート)");
        System.out.println("- http://localhost:8080/login (ログインページ)");
        System.out.println("- http://localhost:8080/first (First View)");
        System.out.println("- http://localhost:8080/view (Tutorial View)");
        System.out.println("================================");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 認証設定
            .authorizeHttpRequests(auth -> auth
                // 静的リソース（CSS、JS、画像など）は認証不要
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                // ログインページは認証不要
                .requestMatchers("/login", "/login/").permitAll()
                // その他のリクエストは認証が必要
                .anyRequest().authenticated()
            )
            // ログイン設定
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/first", true) // ログイン成功後のリダイレクト先
                .permitAll()
            )
            // ログアウト設定
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            // CSRF（クロスサイトリクエストフォージェリ）を無効化（開発中のみ推奨）
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // パスワードエンコーダーを使用
        PasswordEncoder passwordEncoder = passwordEncoder();
        
        // 平文パスワードをBCryptでハッシュ化
        String encodedPassword = passwordEncoder.encode("password");
        
        System.out.println("=== UserDetailsService 初期化 ===");
        System.out.println("エンコードされたパスワード: " + encodedPassword);
        System.out.println("=================================");
        
        UserDetails user = User.builder()
            .username("user")
            .password(encodedPassword) // エンコードされたパスワードを使用
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
