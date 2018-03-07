<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Subluminal Store</title>
    <link rel="stylesheet" href="/style.css">
    <link href="https://fonts.googleapis.com/css?family=Press+Start+2P" rel="stylesheet">
  </head>
  <body>
    <img class="store-img" src="./images/logo_store.png" alt="">
    <div class="img-container">
    <?php
      $dic = "./merch/";
      $imgs = glob($dic. "*.{PNG,png,gif}", GLOB_BRACE);

      foreach($imgs as $img){
        echo '<div class="img-wrapper">';
        echo '<img src="'.$img.'" alt="" />';
        echo '<h2 class="item-price">Price: '.rand(10, 100).'.-</h2>';
        // echo '<input class="item-amount" type="text" size="10" />';
        echo '<div>';
        echo '<button class="item-button button" type="button" min="1" step="any">Add to Cart</button>';
        echo '</div>';
        echo '</div>';
      }
     ?>
   </div>
  </body>
</html>
