package online.omnia.statistics;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lollipop on 16.01.2018.
 */
public class Main {
    public static void main(String[] args) {
        List<AccountsEntity> accountsEntities = MySQLDaoImpl.getInstance().getAccountsEntities("adcash");
        String answer;
        List<NameValuePair> nameValuePairList;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(String.class, new JsonTokenDeserializer());
        Gson gson = gsonBuilder.create();
        String advId = "73363";
        String token;
        for (AccountsEntity accountsEntity : accountsEntities) {
            nameValuePairList = new ArrayList<>();
            nameValuePairList.add(new BasicNameValuePair("username", "buyers@omni-a.com"/*accountsEntity.getUsername()*/));
            nameValuePairList.add(new BasicNameValuePair("password", "0pU8nQfTaFj2"/*accountsEntity.getPassword()*/));

            answer = HttpMethodUtils.postMethod("https://api.myadcash.com/api/v1/auth/", nameValuePairList);

            token = gson.fromJson(answer, String.class);
            answer = HttpMethodUtils.getMethod("https://api.myadcash.com/api/v1/advertiser-report?advertiser_id="
                    + advId
                    + "&start_date=2018-01-01&end_date=2018-01-18&group_by=date,campaignid,country,devicetype,platformname&devicetype=Desktop", token);
            System.out.println(answer);
        }
        MySQLDaoImpl.getSessionFactory().close();
    }
}
