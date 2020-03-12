// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.gson.Gson;
import com.google.sps.data.Comment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import java.util.Enumeration;

/** Servlet responsible for listing comments. */
@WebServlet("/translate-comments")
public class TranslationServlet extends HttpServlet {

    private static String DEFAULT_LANGUAGE_CODE = "es";
    Translate translate = TranslateOptions.getDefaultInstance().getService();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { 
        doGet(request, response);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
        String languageCode = request.getParameter("languageCode");
        Gson gson = new Gson();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(gson.toJson(getCommentList(languageCode)));
    }

    private List<Comment> getCommentList(String languageCode){
        Query query = new Query("Comment").addSort("date", SortDirection.DESCENDING);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        List<Comment> comments = new ArrayList<>();
        for (Entity entity : results.asIterable()) {
            long id = entity.getKey().getId();
            String originalText = (String) entity.getProperty("context");
            Date date = (Date) entity.getProperty("date");

            // Do the translation.
            Translate translate = TranslateOptions.getDefaultInstance().getService();
            Translation translation =
                translate.translate(originalText, Translate.TranslateOption.targetLanguage(languageCode));
            String translatedText = translation.getTranslatedText();

            Comment comment = new Comment(id, translatedText, date);
            comments.add(comment);
        }
        return comments;
    }
}
