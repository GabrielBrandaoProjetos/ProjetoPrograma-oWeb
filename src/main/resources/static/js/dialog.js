$(document).ready(function(){
        $("#opener").click(function(){
          $("#dialog").dialog({
            show: {
              effect: "blind",
              duration: 1000
            },
            hide: {
              effect: "clip",
              duration: 1000
            }
          });
        });
    });