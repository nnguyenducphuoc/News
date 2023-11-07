package com.phuoc.news.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phuoc.news.R;
import com.phuoc.news.activity.DetailNewsActivity;
import com.phuoc.news.adapter.ItemDetailAdapter;
import com.phuoc.news.interfacee.IClickItemDetailsListener;
import com.phuoc.news.model.New_Details;
import com.phuoc.news.myutils.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TrangChuFragment extends Fragment {
//    private Disposable disposable;
    private ItemDetailAdapter itemDetailAdapter;
    private List<New_Details> newDetailsList;

    RecyclerView recycle_view_home;

    public TrangChuFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        recycle_view_home = view.findViewById(R.id.recycle_view_home);
        initUi();
        recycle_view_home.setAdapter(itemDetailAdapter);
        recycle_view_home.setLayoutManager(new LinearLayoutManager(getContext()));


        new ReadRSS().execute("https://cdn.24h.com.vn/upload/rss/trangchu24h.rss");
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
            NodeList nodeListItem1 = document.getElementsByTagName("img");

            String tieuDe = "";
            String desc = "";
            String release_date = "";
            String urlNew = "";
            String image = "";

            if (nodeListItem.getLength() > 0) {

                for (int i = 0; i < nodeListItem.getLength(); i++) {
                    Element elementItem = (Element) nodeListItem.item(i);
                    Element elementItem1 = (Element) nodeListItem1.item(i);
                    tieuDe = parser.getValue(elementItem, "title");
                    release_date = parser.getValue(elementItem, "pubDate");
                    urlNew = parser.getValue(elementItem, "link");
                    desc = parser.getValue(elementItem, "description");
                    image = elementItem1.getAttribute("src");;

                    newDetailsList.add(new New_Details(tieuDe, desc, image, release_date, urlNew));
                }
            }
            itemDetailAdapter.notifyDataSetChanged();
        }
    }





//    private void configDetailsList() throws IOException {
//        Observable<New_Details> newDetailsObservable = getObservable();
//        Observer<New_Details> newDetailsObserver = getNewDetailsObserver();
//
//        newDetailsObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(newDetailsObserver);
//    }

    private void initUi() {
        newDetailsList = new ArrayList<>();
        itemDetailAdapter = new ItemDetailAdapter(newDetailsList, new IClickItemDetailsListener() {
            @Override
            public void OnClickItemNewDetails(New_Details newDetails) {
                Intent intent = new Intent(getContext(), DetailNewsActivity.class);
                intent.putExtra("News", newDetails);
                startActivity(intent);
            }
        });
    }

//    private Observer<New_Details> getNewDetailsObserver() {
//        return new Observer<New_Details>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(@NonNull New_Details newDetails) {
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//    }

//    private Observable<New_Details> getObservable() throws IOException {
//
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//                    urll = fetchDataFromUrl("https://cdn.24h.com.vn/upload/rss/trangchu24h.rss");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();
//
//        newDetailsList = getNewDetailsList(urll);
//        return Observable.create(new ObservableOnSubscribe<New_Details>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<New_Details> emitter) throws Throwable {
//                if (newDetailsList == null || newDetailsList.isEmpty()) {
//                    if (!emitter.isDisposed()) {
//                        emitter.onError(new Exception());
//                    }
//                }
//
//                for (New_Details newDetails : newDetailsList) {
//                    if (!emitter.isDisposed()) {
//                        emitter.onNext(newDetails);
//                    }
//                }
//
//                if (!emitter.isDisposed()) {
//                    emitter.onComplete();
//                }
//            }
//        });
//    }
//
//
//    private String fetchDataFromUrl(String... strings) throws IOException {
//        StringBuilder content = new StringBuilder();
//        try {
//            URL url = new URL(strings[0]);
//            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            String line = "";
//
//            while ((line = bufferedReader.readLine()) != null) {
//                content.append(line);
//            }
//
//            bufferedReader.close();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Log.v("Tag", content.toString());
//        return content.toString();
//    }
//
//
//
//    private List<New_Details> getNewDetailsList(String s) {
//        return getMyResources(s);
//    }
//
//    private List<New_Details> getMyResources(String s) {
//        XMLDOMParser parser = new XMLDOMParser();
//        Document document = parser.getDocument(s);
//
//        NodeList nodeListItem = document.getElementsByTagName("item");
//
//        String tieuDe = "";
//        String desc = "";
//        String release_date = "";
//        String imgUrl = "";
//        String image = "";
//        String desc_demo = "";
//
//        if (nodeListItem.getLength() > 0) {
//
//            for (int i = 0; i < nodeListItem.getLength(); i++) {
//                Element elementItem = (Element) nodeListItem.item(i);
//                tieuDe = parser.getValue(elementItem, "title");
//                release_date = parser.getValue(elementItem, "pubDate");
//                imgUrl = parser.getValue(elementItem, "link");
//                arrayLink.add(imgUrl);
//                desc_demo = parser.getValue(elementItem, "description");
//
//                // Trích xuất dữ liệu trong CDATA
//                Pattern cdataPattern = Pattern.compile("<!\\[CDATA\\[ (.*?) \\]\\]>");
//                Matcher cdataMatcher = cdataPattern.matcher(desc_demo);
//
//                String cdataContent = "";
//                if (cdataMatcher.find()) {
//                    cdataContent = cdataMatcher.group(1);
//                }
//
//                // Trích xuất URL từ thẻ <img>
//                cdataPattern = Pattern.compile("<img src=\"(.*?)\"");
//                cdataMatcher = cdataPattern.matcher(cdataContent);
//
//                if (cdataMatcher.find()) {
//                    image = cdataMatcher.group(1);
//                }
//
//
//                // Trích xuất nội dung liên quan đến Thủ tướng Hà Lan
//                cdataPattern = Pattern.compile("</a></br>(.*?) đánh giá");
//                cdataMatcher = cdataPattern.matcher(cdataContent);
//
//                if (cdataMatcher.find()) {
//                    desc = cdataMatcher.group(1);
//                }
//
//                newDetailsList.add(new New_Details(tieuDe, desc, image, release_date));
//
//
//            }
//        }
//        itemDetailAdapter.notifyDataSetChanged();
//        return newDetailsList;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (disposable != null) {
//            disposable.dispose();
//        }
//    }
}