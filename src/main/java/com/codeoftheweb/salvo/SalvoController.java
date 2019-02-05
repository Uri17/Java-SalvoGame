package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.apache.catalina.User;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api")

public class SalvoController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;


    @Autowired
    private ShipRepository shipRepository;

    @Autowired
    private SalvoRepository salvoRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<Object> register(String userName, String email, String pwd) {

        if (userName.isEmpty() || email.isEmpty() || pwd.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (playerRepository.findByUserName(userName) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        playerRepository.save(new Player(userName, email, pwd));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @RequestMapping("/players")

    public List<Object> getPlayers() {
        return playerRepository.findAll().stream().map(Player -> playerDTO(Player)).collect(toList());
    }
//
//    public Map<String, Object> getPlayer(Authentication authentication) { No need to do this on players
//
//        Map<String, Object> dto = new HashMap();
//
//        if (authentication != null){
//
//            Player NewPlayer = playerRepository.findByEmail(authentication.getName());
//
//            dto.put("NewName", NewPlayer.getUserName());
//            dto.put("NewPlayer", NewPlayer.getEmail());
//
//        }

//        dto.put("players", playerRepository.findAll().stream().map(Player -> playerDTO(Player)).collect(toList()));
//
//        return dto;
//
//    }



    @RequestMapping("/games")

    public Map<String, Object> getLoggedPlayerGame(Authentication authentication) {

        Map<String, Object> dto = new HashMap();

        if (authentication != null) {
            Player loggedPlayer = playerRepository.findByUserName(authentication.getName());

            dto.put("UserLogged", loggedPlayer.getUserName());
            dto.put("playerLogged", loggedPlayer.getEmail());
            //dto.put("id", loggedPlayer.getId());


        }
        dto.put("games", gameRepository.findAll().stream().map(Game -> gameDTO(Game)).collect(toList()));

        return dto;

    }


    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    @RequestMapping("/gamePlayers")
    public List<Object> getGamePlayer() {
        return gamePlayerRepository.findAll().stream().map(GamePlayer -> gamePlayerDTO(GamePlayer)).collect(toList());
    }

    @RequestMapping("/ships")
    public List<Object> getShipLocations() {
        return shipRepository.findAll().stream().map(Ship -> shipDTO(Ship)).collect(toList());
    }

    @RequestMapping("/salvoes")
    public List<Object> getSalvoLocations() {
        return salvoRepository.findAll().stream().map(Salvo -> salvoDTO(Salvo)).collect(toList());
    }

    @RequestMapping("/scores")
    public List<Object> getScore() {
        return scoreRepository.findAll().stream().map(Score -> scoreDTO(Score)).collect(toList());
    }


    @RequestMapping("/leaderboard")
    public Map<String, Object> getPoints() {

        Map<String, Object> allPlayers = new HashMap<>();

        List<GamePlayer> AllGamePlayers = gamePlayerRepository.findAll();

        for (int i = 0; i < AllGamePlayers.size(); i++) {

            String userName = AllGamePlayers.get(i).getPlayer().getUserName();
            if (!allPlayers.containsKey(userName)) {

                Map<String, Object> playerScore = new HashMap<>();

                playerScore.put("wins", AllGamePlayers.get(i).getPlayer().getScore().stream().filter(score -> score.getPoints() == 1).count());
                playerScore.put("losses", AllGamePlayers.get(i).getPlayer().getScore().stream().filter(score -> score.getPoints() == 0).count());
                playerScore.put("ties", AllGamePlayers.get(i).getPlayer().getScore().stream().filter(score -> score.getPoints() == 0.5).count());
//                playerScore.put("total", AllGamePlayers.get(i).getPlayer().getScore().stream().mapToDouble(score -> score.getPoints()).sum());

                double total_wins = Double.parseDouble(playerScore.get("wins").toString());
                double total_ties = Double.parseDouble(playerScore.get("ties").toString());
                double total = (total_wins * 1) + (total_ties * 0.5);
                playerScore.put("total", total);
                allPlayers.put(userName, playerScore);

            }

        }

        return allPlayers;
    }


    @RequestMapping("/game_view/{gamePlayerId}")
    public ResponseEntity<Map<String, Object>> findGamePlayer(@PathVariable Long gamePlayerId, Authentication authentication) {
        GamePlayer gamePlayer = gamePlayerRepository.getOne(gamePlayerId);
        Player loggedPlayer = playerRepository.findByUserName(authentication.getName());

        //if (isGuest(authentication)) {

        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "You're Not Logged In!"), HttpStatus.UNAUTHORIZED);
        }

        if (gamePlayer.getPlayer().getId() == loggedPlayer.getId()) {
            return new ResponseEntity<>(GameViewDTO(gamePlayer.getGame(), gamePlayer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(makeMap("error", "You are not allowed to access to this Game page!"), HttpStatus.UNAUTHORIZED);
        }


    }


    private Map<String, Object> GameViewDTO(Game game, GamePlayer gamePlayer) {

        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", gamePlayer.getGame().getId());
        dto.put("created", gamePlayer.getGame().getDate());
        dto.put("gamePlayers", gamePlayer.getGame().getGamePlayer()
                .stream()
                .map(GamePlayer -> gamePlayerDTO(GamePlayer))
                .collect(toList()));

        dto.put("ships", gamePlayer.getShip().stream().map(Ship -> shipDTO(Ship)).collect(toList()));
        dto.put("salvoes", gamePlayer.getSalvo().stream().map(Salvo -> salvoDTO(Salvo)).collect(toList()));
        dto.put("salvoesOpponent", getOpponent(gamePlayer).getSalvo().stream().map(Salvo -> salvoDTO(Salvo)).collect(toList()));


        return dto;
    }

    private Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }



    @RequestMapping(path = "/games", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createGame(Authentication authentication) {

        Player loggedPlayer = playerRepository.findByUserName(authentication.getName());
        if (authentication == null){
            return new ResponseEntity<>(makeMap("error", "You can't create a New Game if You're Not Logged In! Please Log in or Sign Up for a new player account."), HttpStatus.UNAUTHORIZED);
        } else {
            Game newGame = gameRepository.save(new Game());
            GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(newGame, loggedPlayer));
            return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
        }
    }


    @RequestMapping(path = "/games/{gameId}/players", method = RequestMethod.POST)
        public ResponseEntity<Map<String, Object>> joinGame(@PathVariable Long gameId, Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(makeMap("error", "You can't join a Game if You're Not Logged In! Please Log in or Sign Up for a new player account."), HttpStatus.UNAUTHORIZED);
        }

        Game gameToJoin = gameRepository.getOne(gameId);
        Player loggedPlayer = playerRepository.findByUserName(authentication.getName());


        if (gameRepository.getOne(gameId) == null) {
            return new ResponseEntity<>(makeMap("error", "No such game."), HttpStatus.FORBIDDEN);
        }

        Integer gamePlayersCount = gameToJoin.getGamePlayer().size();

        if (gamePlayersCount == 1) {
            GamePlayer newGamePlayer = gamePlayerRepository.save(new GamePlayer(gameToJoin, loggedPlayer));
            return new ResponseEntity<>(makeMap("gpid", newGamePlayer.getId()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(makeMap("error", "Game is full!"), HttpStatus.FORBIDDEN);
        }

    }



    private Map<String, Object> scoreDTO(Score score) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("games", gameDTO(score.getGame()));
        dto.put("players", playerDTO(score.getPlayer()));
        dto.put("score", score.getPoints());
        dto.put("finishDate", score.getFinishDate());

        return dto;
    }

    private Map<String, Object> shipDTO(Ship ship) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("type", ship.getType());
        dto.put("locations", ship.getShipLocations());

        return dto;
    }


    private Map<String, Object> salvoDTO(Salvo salvo) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("turn", salvo.getTurn());
        dto.put("players", playerDTO(salvo.getGamePlayer().getPlayer()));
        dto.put("locations", salvo.getSalvoLocations());

        return dto;
    }

    private Map<String, Object> gameDTO(Game game) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", game.getId());
        dto.put("created", game.getDate());
        dto.put("gamePlayers", game.getGamePlayer().stream().map(gamePlayer -> gamePlayerDTO(gamePlayer))
                .collect(toList()));
        //dto.put("scores", game.getScore().stream().map(Score -> scoreDTO(Score)).collect(toList()));
        return dto;
    }

    private Map<String, Object> playerDTO(Player player) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", player.getId());
        dto.put("userName", player.getUserName());
        dto.put("email", player.getEmail());
        return dto;
    }

    private Map<String, Object> gamePlayerDTO(GamePlayer gamePlayer) {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("gpid", gamePlayer.getId());
        dto.put("player", playerDTO(gamePlayer.getPlayer()));
        if (gamePlayer.getScore() != null) {
            dto.put("scores",gamePlayer.getScore().getPoints());
        }


        return dto;
    }



    // Common Methods//


    private GamePlayer getOpponent(GamePlayer gamePlayer) {
        GamePlayer opponentGamePlayer = new GamePlayer();

        for (GamePlayer gp : gamePlayer.getGame().getGamePlayer()) {

            if (gp.getId() != gamePlayer.getId()) {
                opponentGamePlayer = gp;
            }
        }

        return opponentGamePlayer;
    }


}










