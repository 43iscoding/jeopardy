package jeopardy.game;

import jeopardy.Bot;
import jeopardy.Saves;
import jeopardy.game.ui.GamePanel;
import jeopardy.game.ui.MainController;
import jeopardy.game.ui.MainDisplay;

import java.util.*;

/**
 * Created by XLIII on 2015-12-10.
 */
public class Game {

    private static Game instance;

    private LinkedList<Round> rounds = new LinkedList<>();
    private Map<Integer, Map<String, List<Round>>> roundsBySection = new HashMap<>();
    private Round currentRound;

    private int currentSection = 1;

    private Bot bot;
    private MainController controller;

    private MainDisplay mainDisplay;

    private GamePanel panel;

    private Set<Player> tutorial;

    public static int Players() {
        return instance.players.size();
    }

    public void setPanel(GamePanel panel) {
        this.panel = panel;
    }

    public void sendMessage(String message) {
        bot.sendMessage(message);
    }

    private Map<String, Player> players = new HashMap<>();

    public Game(GameConfig cfg) {
        instance = this;
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
                roundsThisTheme.add(new Round(theme.name, score, round));
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
            roundsBySection.put(section, new HashMap<String, List<Round>>());
        }

        roundsBySection.get(section).put(theme, rounds);
    }

    public void registerPlayer(String name) {
        Player player = new Player(name);
        Saves.saveScore(player);
        Saves.saveName(player);
        players.put(name, player);
        panel.registerPlayer(player);

        System.out.println("Player" + player.getId() + ": " + name);
    }

    public void start() {
        if (Config.MANUAL_SELECTION) {
            mainDisplay = new MainDisplay(this);
            mainDisplay.startSection(currentSection, roundsBySection.get(currentSection));
            panel.onThemeList();
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

    public void startRound(Round round) {
        currentRound = round;
        currentRound.start();
        panel.onNewRound(currentRound);
        panel.onRoundStarted(currentRound);
    }

    private void gameComplete() {
        System.out.println("Game Complete!");

        Player winner = getWinner();
        controller.onGameComplete(winner);

        StringBuilder sb = new StringBuilder("Final results:\n");
        sb.append(getPrintScoresString(true, false));
        sb.append("Congratulations to ");
        sb.append(winner.getName());
        sb.append("!!!");
        bot.sendMessage(sb.toString());
        mainDisplay.showResults(sortedPlayers());
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
        bot.sendMessage("Current scores: \n" + getPrintScoresString(true, false));
    }

    public String getPrintScoresString(boolean result, boolean html) {
        String delim = html ? "<br>" : "\n";
        StringBuilder sb = new StringBuilder();
        if (result) {
            List<Player> list = new ArrayList<Player>(players.values());
            Collections.sort(list);
            for (int i = 0; i < list.size(); i++) {
                Player player = list.get(i);
                sb.append(i + 1).append(") ").append(player).append(delim);
            }
        } else {
            for (Player player : players.values()) {
                sb.append(player).append(delim);
            }
        }
        return sb.toString();
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public void receiveMessage(String name, String message) {
        if (currentRound == null) return;

        if (!currentRound.started()) return;

        Player player = players.get(name);
        if (player == null) {
            System.out.println("ERROR! NO PLAYER WITH NAME : " + name);
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
        currentRound = new Round("Tutorial", 0, new RoundConfig("Tutorial", "Tutorial"));
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
        bot.sendMessage(mainDisplay.printSelectionPanel());
    }
}
