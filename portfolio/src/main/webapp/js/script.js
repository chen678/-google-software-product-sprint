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



// Todo: implement a working responsive top navigation tabs
function responsiveTopNav() {
    var x = document.getElementById("myTopnav");
    if (x.className === "topnav") {
        x.className += " responsive";
    } else {
        x.className = "topnav";
    }
}

/** Fetches the list of all user comments and builds the UI. */
function getComments(){
    fetch('/data').then(response => response.json()).then((commentsEntries) => {
        // Build the list of history comments.
        const historyOfComments = document.getElementById('history');
        if(commentsEntries.history.length == 0){
            historyOfComments.appendChild(createListElement("OH! Be the first one to leave a message!"));
        } else{
            commentsEntries.history.forEach((line) => {
                historyOfComments.appendChild(createListElement(line));
            });
        }
    });
}

/** Creates an <li> element containing text. */
function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}