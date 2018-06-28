$(document).ready(function(){
      $("#phone").mask("(99) 9-9999-9999");
      $("#phone").on("blur", function() {
          var last = $(this).val().substr( $(this).val().indexOf("-") + 1 );
        if( last.length == 5 ) {
            var move = $(this).val().substr( $(this).val().indexOf("-") + 1, 1 );
          var lastfour = last.substr(1,4);
          var first = $(this).val().substr( 0, 9 );
          $(this).val( first + move + '-' + lastfour );
          }
        });
      });