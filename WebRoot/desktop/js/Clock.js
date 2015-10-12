                            tmpDate = new Date();
                            date = tmpDate.getDate();
                            month = tmpDate.getMonth() + 1;
                            year = tmpDate.getYear();
                            year = (year < 1900) ? (1900 + year) : year;
                            document.write(year);
                            document.write("年");
                            document.write(month);
                            document.write("月");
                            document.write(date);
                            document.write("日 ");

                            myArray = new Array(6);
                            myArray[0] = "星期日"
                            myArray[1] = "星期一"
                            myArray[2] = "星期二"
                            myArray[3] = "星期三"
                            myArray[4] = "星期四"
                            myArray[5] = "星期五"
                            myArray[6] = "星期六"
                            weekday = tmpDate.getDay();
                            if (weekday == 0 | weekday == 6) {
                                document.write(myArray[weekday])
                            }
                            else {
                                document.write(myArray[weekday])
                            }

                            var c = "<font color='#aaaaaa'>"
                            var d = "</font>"

                            document.write(' <span id="clock3"></span>');

                            function showclock3() {
                                var date3 = new Date();
                                var hour3 = date3.getHours();
                                var min3 = date3.getMinutes();
                                var sec3 = date3.getSeconds();
                                var col3 = ":";
                                var x3;
                                x3 = hour3;
                                if (x3 >= 24) x3 = x3 - 24;
                                if (min3 <= 9) min3 = "0" + min3;
                                if (sec3 <= 9) sec3 = "0" + sec3;

                                document.getElementById("clock3").innerHTML = c + x3 + col3 + min3 + col3 + sec3 + d;

                            }
                            setInterval("showclock3()", 1000);

