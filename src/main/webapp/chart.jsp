<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!--Load the AJAX API-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        
        // 컬럼은 쿼리하는 select 컬럼의 데이터 타입과 동일해야 한다.
        data.addColumn('string', '날짜');
        data.addColumn('number', '일별합계');
        
        var arr = [];
        // ajax로 DB에서 데이터 가져온 후 addRows에 삽입
        $.ajax({
        	url:'getChartData',
        	// ajax는 default값이 비동기이기 때문에 설정값을 모두 거치지 않고 바로 넘어간다.
        	// 따라서 false 설정으로 '동기'로 구현하여 설정값을 가져가도록 설정해야 차트에 변환된 값이 온전히 적용된다.
        	async: false, 
        	dataType: 'json',
        	success:function(result){
        		// for looping 이유 => 서버에서 바당온 데이터 형식 변경 / [{},{}] -> [[],[]]
        		for(var temp of result){ // for of, for in 확실히 구분할 것!
        			// mybatis 쿼리 리턴 타입이 map이고, map은 쿼리 타입을 그대로 따라간다(타입 임의 설정이 가능한 VO와는 다르다)
        			// 합계는 bigDecimal이기 때문에 parseInt 필요 노노
        			arr.push([temp.날짜, temp.합계]); 
        		}
	        	console.log(arr);
        	}
       	});
        data.addRows(arr);

        // Set chart options
        var options = {'title':'일별 매출',
                       'width':400,
                       'height':300,
                       vAxis: { format: "$#,###", gridlines: { count: 10 } }
                       };

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>

  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>
  </body>
</html>