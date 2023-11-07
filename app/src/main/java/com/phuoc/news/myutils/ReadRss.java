//package com.phuoc.news.myutils;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.phuoc.news.activity.DetailNewsActivity;
//import com.phuoc.news.model.New_Details;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//    public class ReadRss extends AsyncTask<String, Void, String> {
//        private List<New_Details> newDetailsList1;
//
//        public List<New_Details> getNewDetailsList() {
//            return newDetailsList1;
//        }
//
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//            StringBuilder content = new StringBuilder();
//            try {
//                URL url = new URL(strings[0]);
//                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//                String line = "";
//
//                while ((line = bufferedReader.readLine()) != null) {
//                    content.append(line);
//                }
//                bufferedReader.close();
//            } catch (MalformedURLException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            return content.toString();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
////            try {
////                XMLDOMParser parser = new XMLDOMParser();
////                Document document = parser.getDocument(s);
////                NodeList nodeListItem = document.getElementsByTagName("item");
////                String x = nodeListItem.toString();
////                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
////                DocumentBuilder builder = factory.newDocumentBuilder();
////                InputSource is = new InputSource(new StringReader(x));
////                Document document2 = builder.parse(is);
////
////                NodeList imgList = document.getElementsByTagName("img");
////                for (int i = 0; i < imgList.getLength(); i++) {
////                    Element imgElement = (Element) imgList.item(i);
////                    String src = imgElement.getAttribute("src");
////                    int width = Integer.parseInt(imgElement.getAttribute("width"));
////                    int height = Integer.parseInt(imgElement.getAttribute("height"));
////
////                    // Now you can use 'src', 'width', and 'height' as needed
////                    System.out.println("src: " + src);
////                    System.out.println("width: " + width);
////                    System.out.println("height: " + height);
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//            Log.v("Tag", "content.toString()");
//            XMLDOMParser parser = new XMLDOMParser();
//            Document document = parser.getDocument(s);
//
//            NodeList nodeListItem = document.getElementsByTagName("item");
//            NodeList nodeListItem1 = document.getElementsByTagName("img");
//
//            String tieuDe = "";
//            String desc = "";
//            String release_date = "";
//            String urlNew = "";
//            String image = "";
//
//            if (nodeListItem.getLength() > 0) {
//                newDetailsList1 = new ArrayList<>();
//                for (int i = 0; i < nodeListItem.getLength(); i++) {
//                    Element elementItem = (Element) nodeListItem.item(i);
//                    Element elementItem1 = (Element) nodeListItem1.item(i);
//                    tieuDe = parser.getValue(elementItem, "title");
//                    release_date = parser.getValue(elementItem, "pubDate");
//                    urlNew = parser.getValue(elementItem, "link");
//                    desc = parser.getValue(elementItem, "description");
//                    image = elementItem1.getAttribute("src");;
//                    newDetailsList1.add(new New_Details(tieuDe, desc, image, release_date, urlNew));
//                }
//                Log.v("Tag", newDetailsList1.toString());
//            }
//        }
//
//
//
//    }
//
//
//
