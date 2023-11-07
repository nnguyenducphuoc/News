package com.phuoc.news.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phuoc.news.R;
import com.phuoc.news.activity.DetailNewsActivity;
import com.phuoc.news.adapter.ItemDetailAdapter;
import com.phuoc.news.interfacee.IClickItemDetailsListener;
import com.phuoc.news.model.New_Details;
import com.phuoc.news.myutils.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class BatDongSanFragment extends Fragment {
    private ItemDetailAdapter itemDetailAdapter;
    private List<New_Details> newDetailsList;

    RecyclerView recycle_view_bds;

    public BatDongSanFragment() {
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bat_dong_san, container, false);
        recycle_view_bds = view.findViewById(R.id.recycle_view_bds);
        newDetailsList = new ArrayList<>();
        itemDetailAdapter = new ItemDetailAdapter(newDetailsList, new IClickItemDetailsListener() {
            @Override
            public void OnClickItemNewDetails(New_Details newDetails) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);
                intent.putExtra("News", newDetails);
                startActivity(intent);
            }
        });
        new ReadRSS().execute("https://cdn.24h.com.vn/upload/rss/taichinhbatdongsan.rss");

        recycle_view_bds.setAdapter(itemDetailAdapter);
        recycle_view_bds.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            try {
//                XMLDOMParser parser = new XMLDOMParser();
//                Document document = parser.getDocument(s);
//                NodeList nodeListItem = document.getElementsByTagName("item");
//                String x = nodeListItem.toString();
//                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                InputSource is = new InputSource(new StringReader(x));
//                Document document2 = builder.parse(is);
//
//                NodeList imgList = document.getElementsByTagName("img");
//                for (int i = 0; i < imgList.getLength(); i++) {
//                    Element imgElement = (Element) imgList.item(i);
//                    String src = imgElement.getAttribute("src");
//                    int width = Integer.parseInt(imgElement.getAttribute("width"));
//                    int height = Integer.parseInt(imgElement.getAttribute("height"));
//
//                    // Now you can use 'src', 'width', and 'height' as needed
//                    System.out.println("src: " + src);
//                    System.out.println("width: " + width);
//                    System.out.println("height: " + height);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);

            NodeList nodeListItem = document.getElementsByTagName("item");

            String tieuDe = "";
            String desc = "";
            String release_date = "";
            String urlNew = "";
            String image = "";
            String htmlContent = "";

            if (nodeListItem.getLength() > 0) {

                for (int i = 0; i < nodeListItem.getLength(); i++) {
                    Element elementItem = (Element) nodeListItem.item(i);
                    tieuDe = parser.getValue(elementItem, "title");
                    htmlContent = parser.getValueDesc(elementItem, "description");
                    release_date = parser.getValue(elementItem, "pubDate");
                    urlNew = parser.getValue(elementItem, "link");
                    desc = parser.getDescContent(htmlContent);
                    image = parser.getImageLink(htmlContent);

                    newDetailsList.add(new New_Details(tieuDe, desc, image, release_date, urlNew));
                }
            }
            itemDetailAdapter.notifyDataSetChanged();
        }
    }
}