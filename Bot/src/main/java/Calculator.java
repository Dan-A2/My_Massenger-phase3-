import ir.sharif.ap.phase3.bot.BotResponse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Calculator {


    public static BotResponse respond(String command, int senderId) {
        BotResponse respond = new BotResponse();
        respond.setSendTo("1");
        List<String> receivers = new LinkedList<>();
        receivers.add(Integer.toString(senderId));
        respond.setReceiver(String.join(",", receivers));
        if (command.equals("/help")) {
            respond.setMessage("input -> /solve<space> your math problem");
        } else if (command.startsWith("/solve")){
            String ebarat = command.substring(7);
            try {
                String ans = deleteparantez(ebarat);
                respond.setMessage(ans);
            } catch (Exception e) {
                respond.setMessage("invalid input");
            }
        } else {
            respond.setMessage("invalid input");
        }
        return respond;
    }

    public static String getFields() {
        return null;
    }

    public static void setFields(String info) {

    }

    static long Questionmark(long t){
        long ans = 0;
        if(t == 0){
            return 1;
        }
        while (t > 0){
            ans += factorial(t % 10);
            t /= 10;
        }
        return ans;
    }

    static long A(long k){
        if(k == 0){
            return k;
        }
        long ans = 0;
        long n = 0;
        while (k > 0){
            ans += k%10;
            n++;
            k /= 10;
        }
        return ans/n;
    }

    static long paranthes(long k){
        if(k < 3){
            return 2;
        }
        long tmp = k-1;
        while (!isprime(tmp)){
            tmp--;
        }
        return tmp;
    }

    static String dollar(String a, String b){
        long a1 = Long.parseLong(a);
        long a2 = Long.parseLong(b);
        StringBuilder sb = new StringBuilder();
        while (a1 > 0 || a2 > 0) {
            long digit = ((a1 % 10) + (a2 % 10)) / 2;
            sb.insert(0, (char) (digit + '0'));
            a1 /= 10;
            a2 /= 10;
        }
        long ans = 0;
        for (int i = 0; i < sb.length(); i++)
            ans = ans * 10 + (sb.charAt(i) - '0');
        return Long.toString(ans);
    }

    static String hashtag (String a, String b){
        return Long.toString(2 * Long.parseLong(a) + (Long.parseLong(b)*Long.parseLong(b)));
    }

    static String deleteparantez(String a){

        if (!hasparanthese(a)) {
            return solve(a);
        }
        for (int i = a.length() - 1; i >= 0; i--) {
            if (a.charAt(i) == '(') {
                for (int j = i + 1; j < a.length(); j++) {
                    if (a.charAt(j) == ')' && j == a.length() - 1) {
                        a = a.substring(0, i) + solve(a.substring(i + 1, j));
                        return deleteparantez(a);
                    } else if (a.charAt(j) == ')' && j != a.length() - 1) {
                        a = a.substring(0, i) + solve(a.substring(i + 1, j)) + a.substring(j + 1);
                        return deleteparantez(a);
                    }
                }
            }
        }

        return deleteparantez(a);
    }

    static String solve(String b){ // no parathesis

        if(!hasanyfunc(b)){
            for (int i = 0; i < b.length(); i++) {
                if(b.charAt(i) != '0'){
                    return b.substring(i);
                }
            }
            return "0";
        }

        for (int i = 0; i < b.length(); i++) { // checking for '?'
            if (b.charAt(i) == '?') {
                int from = from(i, b);
                if(i == b.length()-1) {
                    b = b.substring(0, from) + Questionmark(Long.parseLong(b.substring(from, i)));
                }
                else{
                    b = b.substring(0, from) + Questionmark(Long.parseLong(b.substring(from, i))) + b.substring(i+1);
                }
                return solve(b);
            }
        }

        for (int i = b.length() - 1; i >= 0; i--) { // checking for '~' & '<'
            if (b.charAt(i) == '~') {
                int to = to(i, b);
                if(to == b.length()-1) {
                    b = b.substring(0, i) + A(Long.parseLong(b.substring(i + 1, to+1)));
                }
                else{
                    b = b.substring(0, i) + A(Long.parseLong(b.substring(i + 1, to+1))) + b.substring(to+1);
                }
                return solve(b);
            } else if (b.charAt(i) == '<') {
                int to = to(i, b);
                if(to == b.length()-1) {
                    b = b.substring(0, i) + paranthes(Long.parseLong(b.substring(i + 1, to+1)));
                }
                else{
                    b = b.substring(0, i) + paranthes(Long.parseLong(b.substring(i + 1, to+1))) + b.substring(to+1);
                }
                return solve(b);
            }
        }

        // for # - + - * - $
        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Character> funcs = new ArrayList<>();
        for (int i = 0; i < b.length(); i++) {
            if(isInt(b.charAt(i))) {
                StringBuilder currentnum = new StringBuilder();
                while (i<b.length() && isInt(b.charAt(i))) {
                    currentnum.append(b.charAt(i));
                    i++;
                }
                numbers.add(currentnum.toString());
                i--;
            }
            else{
                funcs.add(b.charAt(i));
            }
        }

        for (int i = funcs.size()-1; i >= 0; i--) { // $
            if(funcs.get(i) == '$'){
                String firstnum = numbers.get(i);
                String secondnum = numbers.get(i+1);
                String ans = dollar(firstnum, secondnum);
                numbers.set(i, ans);
                numbers.remove(i+1);
                funcs.remove(i);
                i = funcs.size();
            }
        }

        for (int i = funcs.size()-1; i >= 0; i--) { // *
            if(funcs.get(i) == '*'){
                String firstnum = numbers.get(i);
                String secondnum = numbers.get(i+1);
                String ans = Long.toString(Long.parseLong(firstnum) * Long.parseLong(secondnum));
                numbers.set(i, ans);
                numbers.remove(i+1);
                funcs.remove(i);
                i = funcs.size();
            }
        }

        for (int i = funcs.size()-1; i >= 0; i--) { // #
            if(funcs.get(i) == '#'){
                String firstnum = numbers.get(i);
                String secondnum = numbers.get(i+1);
                String ans = hashtag(firstnum, secondnum);
                numbers.set(i, ans);
                numbers.remove(i+1);
                funcs.remove(i);
                i = funcs.size();
            }
        }

        for (int i = funcs.size()-1; i >= 0; i--) { // +
            if(funcs.get(i) == '+'){
                String firstnum = numbers.get(i);
                String secondnum = numbers.get(i+1);
                String ans = Long.toString(Long.parseLong(firstnum) + Long.parseLong(secondnum));
                numbers.set(i, ans);
                numbers.remove(i+1);
                funcs.remove(i);
                i = funcs.size();
            }
        }

        b = numbers.get(0);

        return solve(b);
    }

    static long factorial(long k){
        if(k == 0){
            return 1;
        }
        return k*factorial(k-1);
    }

    static boolean isprime(long k){
        if(k == 1 || k == 2){
            return true;
        }
        if(k % 2 == 0){
            return false;
        }
        for (int i = 3; (long) i *i <= k; i+=2) {
            if(k%i == 0){
                return false;
            }
        }
        return true;
    }

    static boolean isInt(char a){
        return a >= '0' && a <= '9';
    }

    static boolean hasanyfunc(String a){
        for (int i = 0; i < a.length(); i++) {
            if(a.charAt(i) == '?' || a.charAt(i) == '$' || a.charAt(i) == '<' || a.charAt(i) == '*' || a.charAt(i) == '+' || a.charAt(i) == '~' || a.charAt(i) == '#'){
                return true;
            }
        }
        return false;
    }

    static boolean hasparanthese(String a){
        for (int i = 0; i < a.length(); i++) {
            if(a.charAt(i) == '(' || a.charAt(i) == ')'){
                return true;
            }
        }
        return false;
    }

    static int to(int loc, String a) {
        for (int i = loc + 1; i < a.length(); i++) {
            if (!isInt(a.charAt(i))) {
                return i-1;
            }
        }
        return a.length() - 1;
    }

    static int from(int loc, String a){
        for (int i = loc - 1; i >= 0; i--) {
            if (!isInt(a.charAt(i))) {
                return i+1;
            }
        }
        return 0;
    }
}