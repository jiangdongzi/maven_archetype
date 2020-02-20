import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessLog {

    private static final String FailName = "C:\\work\\filteredLogs";
    private static final String ReqName = "C:\\work\\filteredLogs";
//    private static final String ReqName = "filteredLogs";
    private static final Pattern logPat = Pattern.compile("(resin-port.*?\\[)([^]]+)");
    private static final Pattern idPat = Pattern.compile(".*?userId:(\\d+).*?sellerId:(\\d+)");
    private static final Pattern tradeIdPat = Pattern.compile("tradeId:(\\d+)");

    private static final Set<String> tradeIdSet = new HashSet<>();

    public static void main(String[] args) throws Exception {
        Set<String> logIds = new HashSet<>();
        HashMap<Long, Set<Long>> ptdUsrIds = new HashMap<>();
        HashMap<Long, Set<String>> ptdTradeIds = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FailName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String logId = getLogId(line);
                logIds.add(logId);
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ReqName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String curLogId = getLogId(line);
                if (!logIds.contains(curLogId)) {
                    continue;
                }
                long partnerId = Long.parseLong(getPartnerId(line));
                long userId = Long.parseLong(getUserId(line));
                if (userId == 113817124L) {
                    System.out.println(curLogId);
                }
                String tradeId = getLogId(line);
                tradeIdSet.add(tradeId);

                if (!ptdTradeIds.containsKey(partnerId)) {
                    ptdTradeIds.put(partnerId, new HashSet<>());
                }
                ptdTradeIds.get(partnerId).add(tradeId);

                if (!ptdUsrIds.containsKey(partnerId)) {
                    ptdUsrIds.put(partnerId, new HashSet<>());
                }
                ptdUsrIds.get(partnerId).add(userId);
            }
        }
        HashMap<Long, Integer> uv = new HashMap<>();
        Map.Entry<Long, Set<Long>> temp = null;
        for (Map.Entry<Long, Set<Long>> longSetEntry : ptdUsrIds.entrySet()) {

            long curPartnerId = longSetEntry.getKey();
            int size = longSetEntry.getValue().size();
            uv.put(curPartnerId, size);
            if (curPartnerId == 10000110) {
                temp = longSetEntry;
            }
        }


        HashMap<Long, Integer> pv = new HashMap<>();
        for (Map.Entry<Long, Set<String>> longSetEntry : ptdTradeIds.entrySet()) {
            long curPartnerId = longSetEntry.getKey();
            System.out.println(curPartnerId);

            int size = longSetEntry.getValue().size();
            pv.put(curPartnerId, size);
        }

        System.out.println(temp);

        System.out.println(tradeIdSet.size());
        System.out.println(uv);
        System.out.println(pv);
    }

    private static String getLogId(String line) {
        Matcher matcher = logPat.matcher(line);
        if (!matcher.find()) {
//            System.out.println("not expected");
            return null;
        }
        return matcher.group(2);
    }

    private static String getUserId(String line) {
        Matcher matcher = idPat.matcher(line);
        if (!matcher.find()) {
//            System.out.println("not expected");
            return "0";
        }
        return matcher.group(1);
    }

    private static String getPartnerId(String line) {
        Matcher matcher = idPat.matcher(line);
        if (!matcher.find()) {
//            System.out.println("not expected");
            return "0";
        }
        return matcher.group(2);
    }

    private static String getTradeId(String line) {
        Matcher matcher = tradeIdPat.matcher(line);
        if (!matcher.find()) {
//            System.out.println("not expected");
            return "0";
        }
        return matcher.group(1);
    }
}
