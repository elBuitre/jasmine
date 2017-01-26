/**
 * This function set the text of the dropdown button accordingly to the selection
 */
 $(function(){

    $(".dropdown-menu li a").click(function(){

      $(".btn:first-child").text($(this).text());
      $(".btn:first-child").val($(this).text());

   });

});