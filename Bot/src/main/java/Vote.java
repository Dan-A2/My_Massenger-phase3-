import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sharif.ap.phase3.bot.BotResponse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Vote implements Bot{

    private Map<Integer, Poll> polls;

    public Vote(Map<Integer, Poll> polls) {
        this.polls = polls;
    }

    @Override
    public BotResponse respond(String command, int senderId) {
        BotResponse respond = new BotResponse();
        int groupId = 0;
        for (int i = 0; i < command.length(); i++) {
            if (command.charAt(i) == ' ') {
                groupId = Integer.parseInt(command.substring(0, i));
                break;
            }
        }
        respond.setSendTo("2");
        respond.setReceiver(Integer.toString(groupId));
        if(command.equals("/help")) {
            respond.setMessage("/newPoll <question>+<2 spaces>+<option>+... /vote <index> /retract /get /end");
        } else if(command.startsWith("/newPoll ")) {
            if(polls.containsKey(groupId)) {
                respond.setMessage("you have an unfinished poll!");
            } else {
                List<String> options = new LinkedList<>();
                String question = null;
                command = command.substring(8)+"  ";
                while(true) {
                    while (!command.isEmpty() && command.charAt(0) == ' ') command = command.substring(1);
                    if (command.isEmpty()) break;
                    int index = command.indexOf("  ");
                    String option = command.substring(0, index);
                    if (question == null) question = option;
                    else if (!options.contains(option)) options.add(option);
                    command = command.substring(index + 2);
                }
                if(options.size()>1){
                    polls.put(groupId,new Poll(question,options));
                    StringBuilder answer = new StringBuilder(question);
                    for(int i=0;i<options.size();i++) answer.append("  ").append(i).append(": ").append(options.get(i));
                    respond.setMessage(answer.toString());
                } else {
                    respond.setMessage("not enough options for vote!");
                }
            }
        } else{
            try {
                if(command.startsWith("/vote ")){
                    polls.get(groupId).addVote(groupId,Integer.parseInt(command.substring(6)));
                    respond.setMessage("voted on: " + command.substring(6));
                }
                else if(command.equals("/retract")){
                    if(polls.get(groupId).remove(senderId)) respond.setMessage("retracted vote");
                }
                else if(command.equals("/get")){
                    respond.setMessage("results so far:"+polls.get(groupId).print());
                }
                else if(command.equals("/end")){
                    respond.setMessage("final results:"+polls.remove(new Integer(groupId)).print());
                }
            } catch(Exception e){
                respond.setMessage("this option does not exist!");
            }
        }
        return respond;
    }

    @Override
    public String getFields() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return(mapper.writeValueAsString(polls));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setFields(String info) {
        if (info == null) {
            polls = new HashMap<>();
        } else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                polls = mapper.readValue(info, new TypeReference<Map<Integer, Poll>>(){});
            } catch (Exception e) {
                e.printStackTrace();
                polls = new HashMap<>();
            }
        }
    }

    static class Poll {

        private final String question;
        private final List<String> options;
        private final Map<String, List<Integer>> votes; // maps the selected option with selectors

        public Poll(String question, List<String> options) {
            this.question = question;
            this.options = options;
            this.votes = new HashMap<>();
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getOptions() {
            return options;
        }

        public Map<String, List<Integer>> getVotes() {
            return votes;
        }

        public void addVote(int userId, int index) {
            remove(userId);
            String option = options.get(index);
            votes.get(option).add(userId);
        }

        public boolean remove(int userId) {
            for (String key : votes.keySet()) {
                if(votes.get(key).remove(new Integer(userId))) {
                    return true;
                }
            }
            return false;
        }

        public String print() {
            int totalCount=0;
            for(Map.Entry<String,List<Integer>> m: votes.entrySet()) totalCount+=m.getValue().size();
            StringBuilder answer= new StringBuilder();
            for(Map.Entry<String,List<Integer>> m: votes.entrySet()){
                answer.append("  ").append(m.getKey()).append(": ");
                if(m.getValue().size()==0) answer.append("0%");
                else answer.append(m.getValue().size() * 100 / totalCount).append("%");
            }
            return answer.toString();
        }
    }
}