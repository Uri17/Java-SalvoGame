package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.WebAttributes;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication

//@RepositoryRestResource
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();

	}



	@Bean
	public CommandLineRunner initData(PlayerRepository repository, GameRepository gameNew, GamePlayerRepository gamePlayNew, ShipRepository shipPlayNew, SalvoRepository salvoPlayNew, ScoreRepository scorePlayNew) {
		return (args) -> {
			// save a couple of customers

			Player p1 = new Player("Jack Bauer", "j.bauer@ctu.gov","24");
			repository.save(p1);

			Player p2= new Player("Chloe O'Brian", "c.obrian@ctu.gov","42");
			repository.save(p2);

			Player p3 = new Player("Kim Bauer", "kim_bauer@gmail.com","kb");
			repository.save(p3);

			Player p4 = new Player("Tony Almeida", "t.almeida@ctu.gov","mole");
			repository.save(p4);


			Game g1 = new Game();
			gameNew.save(g1);

			Game g2 = new Game();
			gameNew.save(g2);

			Game g3 = new Game();
			gameNew.save(g3);

			Game g4 = new Game();
			gameNew.save(g4);

			Game g5 = new Game();
			gameNew.save(g5);

			Game g6 = new Game();
			gameNew.save(g6);

			Game g7 = new Game();
			gameNew.save(g7);

			Game g8 = new Game();
			gameNew.save(g8);



			GamePlayer gp1 = new GamePlayer(g1,p1);
			gamePlayNew.save(gp1);

			GamePlayer gp2 = new GamePlayer(g1,p2);
			gamePlayNew.save(gp2);

			GamePlayer gp3 = new GamePlayer(g2,p1);
			gamePlayNew.save(gp3);

			GamePlayer gp4 = new GamePlayer(g2,p2);
			gamePlayNew.save(gp4);

//			GamePlayer gp5 = new GamePlayer(g3,p3);
//			gamePlayNew.save(gp5);

			GamePlayer gp6 = new GamePlayer(g3,p2);
			gamePlayNew.save(gp6);

			GamePlayer gp7 = new GamePlayer(g3,p4);
			gamePlayNew.save(gp7);

			GamePlayer gp8 = new GamePlayer(g4,p2);
			gamePlayNew.save(gp8);

			GamePlayer gp9 = new GamePlayer(g4,p1);
			gamePlayNew.save(gp9);

			GamePlayer gp10 = new GamePlayer(g5,p4);
			gamePlayNew.save(gp10);

			GamePlayer gp11 = new GamePlayer(g5,p1);
			gamePlayNew.save(gp11);

			GamePlayer gp12 = new GamePlayer(g6,p3);
			gamePlayNew.save(gp12);

			GamePlayer gp13 = new GamePlayer(g7,p4);
			gamePlayNew.save(gp13);

			GamePlayer gp14 = new GamePlayer(g8,p3);
			gamePlayNew.save(gp14);

			GamePlayer gp15 = new GamePlayer(g8,p4);
			gamePlayNew.save(gp15);




            Ship s1 = new Ship(gp1,"Destroyer", Arrays.asList("H2","H3","H4"));
            shipPlayNew.save(s1);

			Ship s2 = new Ship(gp1,"Submarine", Arrays.asList("E1","F1","G1"));
			shipPlayNew.save(s2);

			Ship s3 = new Ship(gp1,"PatrolBoat", Arrays.asList("B4","B5"));
			shipPlayNew.save(s3);

			Ship s4 = new Ship(gp2,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s4);

			Ship s5 = new Ship(gp2,"PatrolBoat", Arrays.asList("F1","F2"));
			shipPlayNew.save(s5);

			Ship s6 = new Ship(gp3,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s6);

			Ship s7 = new Ship(gp3,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s7);

			Ship s8 = new Ship(gp4,"Submarine", Arrays.asList("A2","A3","A4"));
			shipPlayNew.save(s8);

			Ship s9 = new Ship(gp4,"PatrolBoat", Arrays.asList("G6","H6"));
			shipPlayNew.save(s9);

			Ship s10 = new Ship(gp6,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s10);

			Ship s11 = new Ship(gp6,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s11);

			Ship s12 = new Ship(gp7,"Submarine", Arrays.asList("A2","A3","A4"));
			shipPlayNew.save(s12);

			Ship s13 = new Ship(gp7,"PatrolBoat", Arrays.asList("G6","H6"));
			shipPlayNew.save(s13);

			Ship s14 = new Ship(gp8,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s14);

			Ship s15 = new Ship(gp8,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s15);

			Ship s16 = new Ship(gp9,"Submarine", Arrays.asList("A2","A3","A4"));
			shipPlayNew.save(s16);

			Ship s17 = new Ship(gp9,"PatrolBoat", Arrays.asList("G6","H6"));
			shipPlayNew.save(s17);

			Ship s18 = new Ship(gp10,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s18);

			Ship s19 = new Ship(gp10,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s19);

			Ship s20 = new Ship(gp11,"Submarine", Arrays.asList("A2","A3","A4"));
			shipPlayNew.save(s20);

			Ship s21 = new Ship(gp11,"PatrolBoat", Arrays.asList("G6","H6"));
			shipPlayNew.save(s21);

			Ship s22 = new Ship(gp12,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s22);

			Ship s23 = new Ship(gp12,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s23);

			Ship s24 = new Ship(gp14,"Destroyer", Arrays.asList("B5","C5","D5"));
			shipPlayNew.save(s24);

			Ship s25 = new Ship(gp14,"PatrolBoat", Arrays.asList("C6","C7"));
			shipPlayNew.save(s25);

			Ship s26 = new Ship(gp15,"Submarine", Arrays.asList("A2","A3","A4"));
			shipPlayNew.save(s26);

			Ship s27 = new Ship(gp15,"PatrolBoat", Arrays.asList("G6","H6"));
			shipPlayNew.save(s27);


            Salvo sa1 = new Salvo(gp1,1, Arrays.asList("B5","C5","F1"));
            salvoPlayNew.save(sa1);

			Salvo sa2 = new Salvo(gp2,1, Arrays.asList("B4","B5","B6"));
			salvoPlayNew.save(sa2);

			Salvo sa3 = new Salvo(gp1,2, Arrays.asList("F2","D5"));
			salvoPlayNew.save(sa3);

			Salvo sa4 = new Salvo(gp2,2, Arrays.asList("E1", "H3", "A2"));
			salvoPlayNew.save(sa4);

			Salvo sa5 = new Salvo(gp3,1, Arrays.asList("A2", "A4", "G6"));
			salvoPlayNew.save(sa5);

			Salvo sa6 = new Salvo(gp4,1, Arrays.asList("B5", "D5", "C7"));
			salvoPlayNew.save(sa6);

			Salvo sa7 = new Salvo(gp3,2, Arrays.asList("A3", "H6"));
			salvoPlayNew.save(sa7);

			Salvo sa8 = new Salvo(gp4,2, Arrays.asList("C5", "C6"));
			salvoPlayNew.save(sa8);

			Salvo sa9 = new Salvo(gp6,1, Arrays.asList("G6", "H6", "A4"));
			salvoPlayNew.save(sa9);

			Salvo sa10 = new Salvo(gp7,1, Arrays.asList("H1", "H2", "H3"));
			salvoPlayNew.save(sa10);

			Salvo sa11 = new Salvo(gp6,2, Arrays.asList("A2", "A3", "D8"));
			salvoPlayNew.save(sa11);

			Salvo sa12 = new Salvo(gp7,2, Arrays.asList("E1", "F2", "G3"));
			salvoPlayNew.save(sa12);

			Salvo sa13 = new Salvo(gp8,1, Arrays.asList("A3", "A4", "F7"));
			salvoPlayNew.save(sa13);

			Salvo sa14 = new Salvo(gp9,1, Arrays.asList("B5", "C6", "H1"));
			salvoPlayNew.save(sa14);

			Salvo sa15 = new Salvo(gp8,2, Arrays.asList("A2", "G6", "H6"));
			salvoPlayNew.save(sa15);

			Salvo sa16 = new Salvo(gp9,2, Arrays.asList("C5", "C7", "D5"));
			salvoPlayNew.save(sa16);

			Salvo sa17 = new Salvo(gp10,1, Arrays.asList("A1", "A2", "A3"));
			salvoPlayNew.save(sa17);

			Salvo sa18 = new Salvo(gp11,1, Arrays.asList("B5", "B6", "C7"));
			salvoPlayNew.save(sa18);

			Salvo sa19 = new Salvo(gp10,2, Arrays.asList("G6", "G7", "G8"));
			salvoPlayNew.save(sa19);

			Salvo sa20 = new Salvo(gp11,2, Arrays.asList("C6", "D6", "E6"));
			salvoPlayNew.save(sa20);

			Salvo sa21 = new Salvo(gp11,3, Arrays.asList("H1","H8"));
			salvoPlayNew.save(sa21);


			Score sc1 = new Score(g1,p1,1.0);
			scorePlayNew.save(sc1);

			Score sc2 = new Score(g1,p2,0.0);
			scorePlayNew.save(sc2);

			Score sc3 = new Score(g2,p1,0.5);
			scorePlayNew.save(sc3);

			Score sc4 = new Score(g2,p2,0.5);
			scorePlayNew.save(sc4);

			Score sc5 = new Score(g3,p2,1.0);
			scorePlayNew.save(sc5);

			Score sc6 = new Score(g3,p4,0.0);
			scorePlayNew.save(sc6);

			Score sc7 = new Score(g4,p2,0.5);
			scorePlayNew.save(sc7);

			Score sc8 = new Score(g4,p1,0.5);
			scorePlayNew.save(sc8);

			//No need to add this scores yet until game finished, and then we add them with a score and also player if it needs.

			/*Score sc9 = new Score(g5,p4, add score points);
			scorePlayNew.save(sc9);

			Score sc10 = new Score(g5,p1,);
			scorePlayNew.save(sc10);

			Score sc11 = new Score(g6,p3,);
			scorePlayNew.save(sc11);

			Score sc12 = new Score(g6,add player,);
			scorePlayNew.save(sc12);

			Score sc13 = new Score(g7,p4,);
			scorePlayNew.save(sc13);

			Score sc14 = new Score(g7,,);
			scorePlayNew.save(sc14);

			Score sc15 = new Score(g8,p3,);
			scorePlayNew.save(sc15);

			Score sc16 = new Score(g8,p4,);
			scorePlayNew.save(sc16);*/




//  We did this with the arrays.asList, so we don't need to create ship types to add locations this way//
/*
			List<String> Destroyer= new ArrayList<>();
			 Destroyer.add("H2");
			 Destroyer.add("H3");
			 Destroyer.add("H4");
			 Destroyer.add("B5");
			 Destroyer.add("C5");
			 Destroyer.add("D5");

			List<String> Submarine= new ArrayList<>();
			Submarine.add("E1");
			Submarine.add("F1");
			Submarine.add("G1");
			Submarine.add("A2");
			Submarine.add("A3");
			Submarine.add("A4");

			List<String> PatrolBoat= new ArrayList<>();
			PatrolBoat.add("B4");
			PatrolBoat.add("B5");
			PatrolBoat.add("F1");
			PatrolBoat.add("F2");
			PatrolBoat.add("C6");
			PatrolBoat.add("C7");
			PatrolBoat.add("G6");
			PatrolBoat.add("H6");*/

			//List<String> Carrier= new ArrayList<>();

			//List<String> Battleship= new ArrayList<>();


		};


	}


	}


@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	PlayerRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inputUser-> {
			Player player = repository.findByUserName(inputUser);
			if (player != null) {
				return new User(player.getUserName(),passwordEncoder.encode(player.getPassword()),
						AuthorityUtils.createAuthorityList("USER"));
			} else {
				throw new UsernameNotFoundException("Unknown user: " + inputUser);
			}
		});
	}

}




@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

				.antMatchers("/web/game.js").hasAuthority("USER")
				.antMatchers("/web/game.html").hasAuthority("USER")
				.antMatchers("/web/game.css").hasAuthority("USER")
				.antMatchers("/web/games.html").permitAll()
				.antMatchers("/web/games.js").permitAll()
				.antMatchers("/web/games.css").permitAll()
				.antMatchers("/api/games").permitAll()
				.antMatchers("/api/players").permitAll()
				.antMatchers("/api/game_view").permitAll()
				.antMatchers("/api/leaderboard").permitAll()

				.and()
				.formLogin();

		http.formLogin()
				.usernameParameter("userName")
				//.EmailParameter("email")
				.passwordParameter("pwd")
				.loginPage("/app/login");



		http.logout().logoutUrl("/app/logout");

		// turn off checking for CSRF tokens
		http.csrf().disable();

		// if user is not authenticated, just send an authentication failure response
		http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if login is successful, just clear the flags asking for authentication
		http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

		// if login fails, just send an authentication failure response
		http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

		// if logout is successful, just send a success response
		http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}


}






