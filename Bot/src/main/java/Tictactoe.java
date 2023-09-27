import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sharif.ap.phase3.bot.BotResponse;

import java.util.*;

public class Tictactoe implements Bot{

    private Map<Integer, XO> gamesRunning; // maps playerId with the game
    private Map<Integer, Integer> waitingRoom; // maps specific Id with waiting player

    public void setGamesRunning(Map<Integer, XO> gamesRunning) {
        this.gamesRunning = gamesRunning;
    }

    public void setWaitingRoom(Map<Integer, Integer> waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public Tictactoe() {
        gamesRunning = new HashMap<>();
        waitingRoom = new HashMap<>();
    }

    public Map<Integer, XO> getGamesRunning() {
        return gamesRunning;
    }

    public Map<Integer, Integer> getWaitingRoom() {
        return waitingRoom;
    }

    @Override
    public BotResponse respond(String command, int senderId) {
        BotResponse response = new BotResponse();
        response.setSendTo("1");
        List<String> receivers = new LinkedList<>();
        receivers.add(Integer.toString(senderId));
        if(command.equals("/help")) {
            response.setReceiver(String.join("," ,receivers));
            response.setMessage("/newGame  /join <invite_code>  /mark <1-9>  /cancel");
        } else{
            try {
                if(command.equals("/newGame")){
                    if(gamesRunning.containsKey(senderId)) {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("finish your current game first");
                    } else{
                        int inviteCode;
                        Random random = new Random();
                        do{
                            inviteCode = random.nextInt(Integer.MAX_VALUE);
                        } while(waitingRoom.containsKey(inviteCode));
                        waitingRoom.put(inviteCode, senderId);
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("send this invite code to your opponent: "+inviteCode);
                    }
                } else if(command.startsWith("/join ")){
                    if(gamesRunning.containsKey(senderId)) {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("finish your current game first");
                    } else if(waitingRoom.containsValue(senderId)) {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("you are in the waiting list");
                    } else {
                        int inviteCode = Integer.parseInt(command.substring(6));
                        if(!waitingRoom.containsKey(inviteCode)) {
                            response.setReceiver(String.join(",", receivers));
                            response.setMessage("invalid or expired invite code");
                        } else {
                            int otherPlayer = waitingRoom.remove(new Integer(inviteCode));
                            XO gameState = new XO(senderId, otherPlayer);
                            gamesRunning.put(otherPlayer,gameState);
                            gamesRunning.put(senderId,gameState);
                            receivers.add(Integer.toString(otherPlayer));
                            response.setReceiver(String.join(",", receivers));
                            response.setMessage("your game is now started\n" + gameState.print());
                        }
                    }
                }
                else if(command.startsWith("/mark ")){
                    if(!gamesRunning.containsKey(senderId)) {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("you are not in a game!");
                    } else if(!gamesRunning.get(senderId).checkIsTurn(senderId)) {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("not your turn");
                    } else {
                        int index = Integer.parseInt(command.substring(6,7))-1;
                        XO game = gamesRunning.get(senderId);
                        int otherPlayerId = game.getOtherPlayerId(senderId);
                        boolean changed = game.putMark(senderId,index);
                        if(changed) {
                            receivers.add(Integer.toString(otherPlayerId));
                            if(game.getResult()>0) {
                                gamesRunning.remove(senderId);
                                switch (game.getResult()) {
                                    case 1:
                                        if (game.getPlayer1Id() == senderId) {
                                            response.setReceiver(String.join(",", receivers));
                                            response.setMessage("winner " + senderId);
                                        } else {
                                            response.setReceiver(String.join(",", receivers));
                                            response.setMessage("winner " + otherPlayerId);
                                        }
                                        break;
                                    case 2:
                                        if (game.getPlayer2Id() == senderId) {
                                            response.setReceiver(String.join(",", receivers));
                                            response.setMessage("winner " + senderId);
                                        } else {
                                            response.setReceiver(String.join(",", receivers));
                                            response.setMessage("winner " + otherPlayerId);
                                        }
                                        break;
                                    case 3:
                                        response.setReceiver(String.join(",", receivers));
                                        response.setMessage("tie");
                                        break;
                                }
                            }
                        } else {
                            response.setReceiver(String.join(",", receivers));
                            response.setMessage("wrong number is entered");
                        }
                        response.setMessage(response.getMessage() + "/n" + game.print());
                    }
                } else if(command.equals("/cancel")){
                    if(gamesRunning.containsKey(senderId)){
                        XO game = gamesRunning.remove(senderId);
                        int otherPlayerId = game.getOtherPlayerId(senderId);
                        gamesRunning.remove(otherPlayerId);
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("game is canceled");
                    } else if(waitingRoom.containsValue(senderId)) {
                        int inviteCode=-1;
                        for(Map.Entry<Integer,Integer> m: waitingRoom.entrySet()){
                            if(m.getValue()==senderId){
                                inviteCode = m.getKey();
                                break;
                            }
                        }
                        waitingRoom.remove(inviteCode);
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("canceled invitation");
                    } else {
                        response.setReceiver(String.join(",", receivers));
                        response.setMessage("nothing to cancel");
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    @Override
    public String getFields() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String f1 = objectMapper.writeValueAsString(gamesRunning);
            String f2 = objectMapper.writeValueAsString(waitingRoom);
            String[] fields = {f1, f2};
            return objectMapper.writeValueAsString(fields);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setFields(String info) {
        if (info == null) {
            gamesRunning = new HashMap<>();
            waitingRoom = new HashMap<>();
        } else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String[] fields = mapper.readValue(info, String[].class);
                gamesRunning = mapper.readValue(fields[0], new TypeReference<Map<Integer, XO>>(){});
                waitingRoom = mapper.readValue(fields[1], new TypeReference<Map<Integer, Integer>>(){});
            } catch (Exception e) {
                e.printStackTrace();
                gamesRunning = new HashMap<>();
                waitingRoom = new HashMap<>();
            }
        }
    }

    static class XO {

        private final int player1Id, player2Id;
        private final List<Cell> cells;
        private int turn; // 1->X / 2->O
        private int result; // 0->playing / 1->X won / 2->O won / 3->tie

        public XO(int player1Id, int player2Id) {
            this.player1Id = player1Id;
            this.player2Id = player2Id;
            this.cells = new LinkedList<>();
            for (int i = 1; i <= 9; i++) {
                cells.add(new Cell(i));
            }
            this.turn = 1;
            this.result = 0;
        }

        public boolean checkIsTurn(int id) {
            return turn == id;
        }

        public boolean putMark(int id, int location) {
            if (location < 0 || location > 8) {
                return false;
            } else if (id == player1Id && cells.get(location).getState() != 'X') {
                cells.get(location).setState('X');
                turn = player2Id;
                checkFinish();
                return true;
            } else if (id == player2Id && cells.get(location).getState() != 'O') {
                cells.get(location).setState('O');
                turn = player1Id;
                checkFinish();
                return true;
            }
            return false;
        }

        public void checkFinish() {
            if (cells.get(0).getState() != '*' && cells.get(0).getState() == cells.get(1).getState() && cells.get(1).getState() == cells.get(2).getState()) {
                finishGame();
            } else if (cells.get(3).getState() != '*' && cells.get(3).getState() == cells.get(4).getState() && cells.get(4).getState() == cells.get(5).getState()) {
                finishGame();
            } else if (cells.get(6).getState() != '*' && cells.get(6).getState() == cells.get(7).getState() && cells.get(7).getState() == cells.get(8).getState()) {
                finishGame();
            } else if (cells.get(0).getState() != '*' && cells.get(0).getState() == cells.get(3).getState() && cells.get(3).getState() == cells.get(6).getState()) {
                finishGame();
            } else if (cells.get(1).getState() != '*' && cells.get(1).getState() == cells.get(4).getState() && cells.get(4).getState() == cells.get(7).getState()) {
                finishGame();
            } else if (cells.get(2).getState() != '*' && cells.get(2).getState() == cells.get(5).getState() && cells.get(5).getState() == cells.get(8).getState()) {
                finishGame();
            } else if (cells.get(0).getState() != '*' && cells.get(0).getState() == cells.get(4).getState() && cells.get(4).getState() == cells.get(8).getState()) {
                finishGame();
            } else if (cells.get(2).getState() != '*' && cells.get(2).getState() == cells.get(4).getState() && cells.get(4).getState() == cells.get(6).getState()) {
                finishGame();
            } else {
                boolean tie = true;
                for (Cell cell : cells) {
                    if (cell.getState() == '*') {
                        tie = false;
                        break;
                    }
                }
                if (tie) {
                    result = 3;
                }
            }
        }

        private void finishGame() {
            if (turn == player1Id) {
                result = 1;
            } else {
                result = 2;
            }
        }

        public int getPlayer1Id() {
            return player1Id;
        }

        public int getPlayer2Id() {
            return player2Id;
        }

        public int getOtherPlayerId(int thisPlayerId) {
            if (thisPlayerId == player1Id) {
                return player2Id;
            } else if (thisPlayerId == player2Id) {
                return player1Id;
            }
            return -2;
        }

        public List<Cell> getCells() {
            return cells;
        }

        public int getTurn() {
            return turn;
        }

        public void setTurn(int turn) {
            this.turn = turn;
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String print() {
            String table = "";
            for (int i = 0; i < cells.size(); i++) {
                table += cells.get(i).getState() + "  ";
                if (i%3 == 2) {
                    table += "\n";
                }
            }
            return table;
        }
    }

    static class Cell {

        private final int location;
        private char state; // X_O_*

        public Cell(int location) {
            this.location = location;
            this.state = '*';
        }

        public int getLocation() {
            return location;
        }

        public char getState() {
            return state;
        }

        public void setState(char state) {
            this.state = state;
        }
    }
}