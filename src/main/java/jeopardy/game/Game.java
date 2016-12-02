package jeopardy.game;

import jeopardy.Config;
import jeopardy.Saves;
import jeopardy.game.bot.Bot;
import jeopardy.game.config.GameConfig;
import jeopardy.game.config.RoundConfig;
import jeopardy.game.config.ThemeConfig;
import jeopardy.game.ui.controller.GamePanel;
import jeopardy.game.ui.MainController;
import jeopardy.game.ui.display.MainDisplay;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Game {

    private static Game instance;

    private LinkedList<Round> rounds = new LinkedList<>();
    private Map<Integer, Map<String, List<Round>>> roundsBySection = new HashMap<>();
    private Round currentRound;

    public Player lastCorrect;

    private int currentSection = 1;

    private Bot bot;
    private MainController controller;

    private MainDisplay mainDisplay;

    private GamePanel panel;

    private Set<Player> tutorial;

    private Set<String> themesTouched = new HashSet<>();

    public static int Players() {
        return instance.players.size();
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }

    public void sendMessage(String message) {
        bot.sendMessage(message);
    }

    public static void updatePlayerName(Player player) {
        instance.bot.updatePlayerName(player);
    }

    public Map<String, Player> players = new HashMap<>();

    private GameConfig config;

    public Game(GameConfig cfg) {
        instance = this;
        config = cfg;
        int currentSection = 1;
        int currentThemeInSection = 0;
        for (ThemeConfig theme : cfg.themes) {

            List<Round> roundsThisTheme = new ArrayList<>();

            int i = 0;
            for (RoundConfig round : theme.rounds) {
                i++;
                int score = i * Config.SCORE_MULTIPLIER;
                if (Config.ROUND_MULTIPLICATOR) {
                    score *= currentSection;
                }
                roundsThisTheme.add(new Round(theme, score, round));
            }
            if (i != Config.ROUND_PER_THEME) {
                System.out.println("WARNING! Bad round number in theme: " + theme.name + "(" + i + " instead of " + Config.ROUND_PER_THEME + ")");
            }

            rounds.addAll(roundsThisTheme);
            insertRoundsToSectionTheme(currentSection, theme.name, roundsThisTheme);

            currentThemeInSection++;
            if (currentThemeInSection % Config.THEMES_PER_SECTION == 0) {
                currentSection++;
            }
        }
    }

    private void insertRoundsToSectionTheme(int section, String theme, List<Round> rounds) {
        if (!roundsBySection.containsKey(section)) {
            roundsBySection.put(section, new HashMap<>());
        }

        roundsBySection.get(section).put(theme, rounds);
    }

    public void registerPlayer(String userId, String name) {
        Player player = new Player(userId, name);
        Saves.saveScore(player);
        Saves.saveName(player);
        players.put(userId, player);
        panel.registerPlayer(player);

        lastCorrect = player;
        System.out.println("Player" + player.getIndex() + ": " + name);
        controller.syncScore();
    }

    public void initDisplay() {
        mainDisplay = new MainDisplay(this);
        mainDisplay.showLogoPanel();
    }

    public void introQuestion() {
        if (Config.MANUAL_SELECTION) {
            mainDisplay.showIntroPanel(config.intro);
            controller.showIntro(players.values());
        } else {
            start();
        }
    }

    public void introWon(Player player) {
        lastCorrect = player;
        start();
    }

    public void start() {
        if (Config.MANUAL_SELECTION) {
            mainDisplay.startSection(currentSection, roundsBySection.get(currentSection));
            panel.onThemeList();
            controller.showGame();
        } else {
            nextRound();
        }
    }

    public void nextRound() {
        currentRound = rounds.poll();
        if (currentRound == null) {
            gameComplete();
            return;
        }

        panel.onNewRound(currentRound);

        bot.sendMessage(currentRound);
        startRound();
    }

    public void startRound() {
        currentRound.start();
        panel.onRoundStarted(currentRound);
    }

    public void onRoundChosen(Round round) {
        if (!themesTouched.contains(round.getTheme())) {
            themesTouched.add(round.getTheme());
            if (round.getTask() != null) {
                mainDisplay.showTaskPanel(round);
                return;
            }
        }
        startRound(round);
    }

    public void startRound(Round round) {
        currentRound = round;
        currentRound.start();
        panel.onNewRound(currentRound);
        panel.onRoundStarted(currentRound);
        showQuestionPanel(round);
    }

    private void gameComplete() {
        System.out.println("Game Complete!");

        if (!isTie()) {
            Player winner = getWinner();
            controller.onGameComplete(winner);

            StringBuilder sb = new StringBuilder("Final results:\n");
            sb.append(getPrintScoresString(false));
            sb.append("Congratulations to ");
            sb.append(winner.getName());
            sb.append("!!!");
            bot.sendMessage(sb.toString());
            mainDisplay.showResults(sortedPlayers());
        } else if (Config.ALLOW_TIE) {
            StringBuilder sb = new StringBuilder("Final results:\n");
            sb.append(getPrintScoresString(false));
            sb.append("Tie between ");
            sb.append(getTied().stream().map(Player::getName).collect(Collectors.joining(" & ")));
            sb.append("!!!");
            bot.sendMessage(sb.toString());
            mainDisplay.showResults(sortedPlayers());
        } else {
            System.out.println("SHOW FINAL QUESTION");
        }
    }

    private Player getWinner() {
        return sortedPlayers().get(0);
    }

    private List<Player> sortedPlayers() {
        List<Player> list = new ArrayList<>(players.values());
        Collections.sort(list);
        return list;
    }

    public void printScores() {
        bot.sendMessage("Current scores: \n" + getPrintScoresString(false));
    }

    public String getPrintScoresString(boolean html) {
        if (players.size() == 0) return "";

        String delim = html ? "<br>" : "\n";
        StringBuilder sb = new StringBuilder();
        List<Player> list = new ArrayList<>(players.values());
        Collections.sort(list);
        for (Player player : list) {
            sb.append(getPlace(player)).append(") ").append(player).append(delim);
        }
        return sb.toString();
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public static int getPlace(Player player) {
        List<Player> list = new ArrayList<>(instance.players.values());
        Collections.sort(list);
        Player lastPlayer = list.get(0);
        int realPlace = 1;
        for (int i = 1; i <= list.size(); i++) {
            Player p = list.get(i - 1);
            if (p.getScore() != lastPlayer.getScore()) {
                realPlace = i;
            }

            lastPlayer = p;

            if (p.equals(player)) {
                return realPlace;
            }
        }
        System.out.println("Could not determine place for " + player);
        return -1;
    }

    private boolean isTie() {
        return getTied().size() > 1;
    }

    public Set<Player> getTied() {
        if (players.size() == 0) return Collections.emptySet();
        Set<Player> tied = new HashSet<>();
        List<Player> list = new ArrayList<>(players.values());
        Collections.sort(list);
        int tiedScore = list.get(0).getScore();
        for (Player p : list) {
            if (p.getScore() == tiedScore) {
                tied.add(p);
            }
        }

        return tied;
    }

    public void receiveMessage(String playerId, String message) {
        if (currentRound == null) return;

        if (!currentRound.started()) return;

        Player player = players.get(playerId);
        if (player == null) {
            System.out.println("ERROR! NO PLAYER WITH ID: " + playerId);
            return;
        }

        if (!matches(message)) {
            return;
        }

        if (tutorial != null && !tutorial.isEmpty()) {
            tutorial.remove(player);
            controller.onTutorialProgress(tutorial.size());
            panel.onCorrect(player);
            if (tutorial.isEmpty()) {
                controller.onTutorialEnded();
            }
            return;
        }

        if (currentRound.offer(player) && !currentRound.ended()) {
            //process player
            panel.onAnswer(player);
        }
    }

    private boolean matches(String message) {
        //Every message matches now
        return true;
    }

    public void endRound() {
        currentRound.end();
        if (mainDisplay.sectionEnded()) {
            panel.offerPrintScores();
        }
        if (currentRound.last()) {
            if (!rounds.isEmpty()) {
                panel.offerPrintScores();
            }
        }
        panel.onRoundEnded(rounds.isEmpty());
        showAnswer();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void correct() {
        Player player = currentRound.correct();
        Saves.saveScore(player);
        panel.onCorrect(player);
        bot.sendMessage(player.getScoreMessage());
        lastCorrect = player;
        controller.syncScore();
        endRound();
    }

    public void incorrect() {
        Player player = currentRound.incorrect();
        Saves.saveScore(player);
        panel.onIncorrect(player);
        bot.sendMessage(player.getScoreMessage());
        if (currentRound.everyoneAnswered()) {
            endRound();
            return;
        }

        if (currentRound.hasPendingPlayers()) {
            panel.onAnswer(currentRound.pendingPlayer());
        } else {
            panel.onRoundStarted(currentRound);
        }
    }

    public String getConfigStr() {
        return "Game initialized with " + (rounds.size() / Config.ROUND_PER_THEME) + " themes & " + Game.Players() + " players";
    }

    public void startTutorial() {
        currentRound = new Round(new ThemeConfig("Tutorial"), 0, new RoundConfig("Tutorial", "Tutorial"));
        currentRound.start();
        tutorial = new HashSet<>(players.values());
        controller.onTutorialStarted();
        controller.onTutorialProgress(tutorial.size());
    }

    //toggle between +N / -N
    public void toggleScore(Player player) {
        if (currentRound == null) {
            return;
        }

        if (currentRound.playerWrong(player)) {
            System.out.println("Wrong to right");
            currentRound.wrongToRight(player);
            Saves.saveScore(player);
            panel.onCorrect(player);
            bot.sendMessage(player.getScoreMessage());
            endRound();
        } else if (currentRound.playerRight(player)) {
            currentRound.rightToWrong(player);
            Saves.saveScore(player);
            panel.onIncorrect(player);
            bot.sendMessage(player.getScoreMessage());

            if (currentRound.everyoneAnswered()) {
                endRound();
                return;
            }

            if (currentRound.hasPendingPlayers()) {
                panel.onAnswer(currentRound.pendingPlayer());
            } else {
                panel.onRoundStarted(currentRound);
            }
        }
    }

    public void showQuestionPanel(Round round) {
        mainDisplay.showQuestionPanel(round);
    }

    public void showAnswer() {
        mainDisplay.showAnswer();
    }

    public void showSelectionPanel() {
        if (mainDisplay.sectionEnded()) {
            currentSection++;
            if (!roundsBySection.containsKey(currentSection)) {
                gameComplete();
            } else {
                mainDisplay.startSection(currentSection, roundsBySection.get(currentSection));
                panel.onThemeList();
                printSelectionPanel();
            }
        } else {
            mainDisplay.showSelectionPanel();
            panel.setThemeSelection();
            printSelectionPanel();
        }
    }

    private void printSelectionPanel() {
        bot.sendUniqueMessage(mainDisplay.printSelectionPanel() + "\n" + lastCorrect.getName() + ", please choose a question.", "selection_panel");
    }

    public void cleanup() {
        bot.cleanup();
    }
}
