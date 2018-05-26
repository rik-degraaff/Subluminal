<template>
  <div class="hello">
    <div id="stars"></div>
    <div id="stars2"></div>
    <div id="stars3"></div>
    <sub-header></sub-header>
    <h1>Social</h1>
    <ul>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://twitter.com/subluminalgame"
          target="_blank"
        >
          Twitter
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://instagram.com/subluminalgame"
          target="_blank"
        >
          Instagram
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="https://www.reddit.com/r/subluminal/"
          target="_blank"
        >
          Reddit
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>
    </ul>
    <h1>Preorder</h1>
    <ul>
      <li class="button-pixel">
        <div class="sub-button-top"></div>
        <div class="sub-button-left"></div>
        <a
          href="http://buy.subluminal.tech/store.steampowered.com/app/427520/Factorio/index.html"
          target="_blank"
        >
          Steam*
          <p class="mini-details">*not an actual steam page</p>
        </a>
        <div class="sub-button-right"></div>
        <div class="sub-button-bottom"></div>
      </li>

    </ul>
        <ul>
          <li class="button-pixel">
            <div class="sub-button-top"></div>
            <div class="sub-button-left"></div>
              <router-link to="/gamerules">Game Rules</router-link>
            <div class="sub-button-right"></div>
            <div class="sub-button-bottom"></div>
          </li>

        </ul>
    <h1>Get The Game</h1>
    <li class="button-pixel">
            <div class="sub-button-top"></div>
            <div class="sub-button-left"></div>
              <a href="http://jar.subluminal.tech/Subluminal.jar" target="_blank">Download</a>
            <div class="sub-button-right"></div>
            <div class="sub-button-bottom"></div>
          </li>
    <li class="button-pixel">
      <div class="sub-button-top"></div>
      <div class="sub-button-left"></div>
        <a href="http://jar.subluminal.tech/Manual.pdf" target="_blank">Manual</a>
      <div class="sub-button-right"></div>
      <div class="sub-button-bottom"></div>
    </li>
  </div>
</template>

<script>
import Header from './Header'
import particles from 'particles.js'
export default {
  name: 'HelloWorld',
  components: {
    'sub-header': Header
  },
  created(){
    particles.load('particles-js', '~@/assets/particlesjs-config.json', function() {
      console.log("callback - paritcles loaded heree");
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #59FF00;
    text-decoration: none;
    font-size: 1.5rem;
  }
  .mini-details{
    font-size: 0.75rem;
  }

  .button-pixel{
    border: 2px solid transparent;
    padding: 10px 5px;
    position: relative;
  }

  .button-pixel:hover{
    border: 2px solid red;
    cursor:pointer;
  }

 .sub-button-top{
    position: absolute;
    top: 0;
    left: 10%;
    margin-top: -5px;
    width: 80%;
    height: 5px;
    background: #101429;
  }

  .sub-button-bottom{
    position: absolute;
    bottom: 0;
    right: 10%;
    margin-bottom: -5px;
    width: 80%;
    height: 5px;
    background: #101429;
  }

  .sub-button-left{
    position: absolute;
    margin-left: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    left: 0;
    background: #101429;
  }

  .sub-button-right{
    position: absolute;
    margin-right: -5px;
    width: 5px;
    height: 70%;
    top: 15%;
    right: 0;
    background: #101429;
  }

  html {
  height: 100%;
  background: radial-gradient(ellipse at bottom, #1b2735 0%, #090a0f 100%);
  overflow: hidden;
}

#stars {
  width: 1px;
  height: 1px;
  background: transparent;
  box-shadow: 1126px 1224px #FFF , 1459px 542px #FFF , 645px 869px #FFF , 420px 1443px #FFF , 551px 937px #FFF , 295px 87px #FFF , 738px 1543px #FFF , 298px 440px #FFF , 1111px 1213px #FFF , 1906px 1036px #FFF , 828px 363px #FFF , 1359px 1533px #FFF , 870px 1294px #FFF , 465px 274px #FFF , 1854px 387px #FFF , 193px 1815px #FFF , 1051px 1463px #FFF , 1619px 1137px #FFF , 1175px 1592px #FFF , 1252px 1438px #FFF , 130px 1647px #FFF , 1492px 1760px #FFF , 578px 1135px #FFF , 1890px 1562px #FFF , 1077px 398px #FFF , 806px 716px #FFF , 1295px 1099px #FFF , 407px 1567px #FFF , 564px 1189px #FFF , 876px 1876px #FFF , 1143px 1149px #FFF , 1872px 1631px #FFF , 907px 1428px #FFF , 261px 713px #FFF , 456px 537px #FFF , 34px 1056px #FFF , 1517px 175px #FFF , 1363px 1156px #FFF , 1149px 1950px #FFF , 417px 1525px #FFF , 1176px 945px #FFF , 1985px 1454px #FFF , 221px 1046px #FFF , 714px 1739px #FFF , 1562px 584px #FFF , 1520px 415px #FFF , 1699px 1087px #FFF , 810px 1275px #FFF , 600px 1592px #FFF , 1647px 345px #FFF , 1402px 940px #FFF , 401px 1759px #FFF , 1393px 1615px #FFF , 1206px 887px #FFF , 11px 122px #FFF , 1411px 764px #FFF , 23px 1323px #FFF , 1651px 1684px #FFF , 1539px 1147px #FFF , 491px 1216px #FFF , 4px 1886px #FFF , 574px 1324px #FFF , 1688px 1891px #FFF , 844px 1572px #FFF , 1122px 858px #FFF , 689px 750px #FFF , 247px 674px #FFF , 905px 1987px #FFF , 1683px 270px #FFF , 407px 1606px #FFF , 640px 1001px #FFF , 1768px 1175px #FFF , 1439px 1179px #FFF , 1637px 1917px #FFF , 1624px 1321px #FFF , 454px 600px #FFF , 1034px 1473px #FFF , 787px 7px #FFF , 1158px 1417px #FFF , 117px 62px #FFF , 1400px 1079px #FFF , 1382px 309px #FFF , 388px 543px #FFF , 1170px 1204px #FFF , 259px 1908px #FFF , 381px 1846px #FFF , 865px 1448px #FFF , 182px 1999px #FFF , 1051px 1584px #FFF , 1907px 1885px #FFF , 253px 1861px #FFF , 949px 154px #FFF , 1571px 1444px #FFF , 1556px 354px #FFF , 1964px 766px #FFF , 1358px 1515px #FFF , 172px 1267px #FFF , 1641px 101px #FFF , 875px 1872px #FFF , 1233px 1593px #FFF , 1003px 65px #FFF , 1163px 487px #FFF , 1906px 33px #FFF , 308px 952px #FFF , 231px 953px #FFF , 1901px 382px #FFF , 618px 310px #FFF , 1766px 1080px #FFF , 1986px 1545px #FFF , 98px 1459px #FFF , 348px 1110px #FFF , 743px 782px #FFF , 1430px 91px #FFF , 1351px 1716px #FFF , 1624px 1948px #FFF , 1375px 158px #FFF , 130px 1880px #FFF , 908px 856px #FFF , 1967px 227px #FFF , 1757px 420px #FFF , 1986px 981px #FFF , 442px 193px #FFF , 1510px 796px #FFF , 1727px 217px #FFF , 302px 1821px #FFF , 1049px 577px #FFF , 469px 649px #FFF , 537px 285px #FFF , 1253px 1963px #FFF , 1538px 514px #FFF , 1140px 552px #FFF , 1144px 1666px #FFF , 400px 1871px #FFF , 1046px 400px #FFF , 610px 967px #FFF , 1833px 255px #FFF , 937px 925px #FFF , 2px 1851px #FFF , 1012px 1784px #FFF , 1606px 1954px #FFF , 503px 1926px #FFF , 1808px 1122px #FFF , 473px 1083px #FFF , 824px 1715px #FFF , 309px 1825px #FFF , 1758px 1540px #FFF , 670px 497px #FFF , 1254px 605px #FFF , 1821px 1571px #FFF , 619px 408px #FFF , 1951px 1718px #FFF , 1217px 1225px #FFF , 1780px 18px #FFF , 958px 1597px #FFF , 1185px 974px #FFF , 1883px 140px #FFF , 253px 1183px #FFF , 1000px 1633px #FFF , 323px 1568px #FFF , 82px 3px #FFF , 622px 228px #FFF , 21px 1344px #FFF , 911px 33px #FFF , 188px 1781px #FFF , 229px 1524px #FFF , 1489px 713px #FFF , 856px 610px #FFF , 35px 455px #FFF , 1281px 745px #FFF , 1161px 234px #FFF , 767px 1404px #FFF , 907px 561px #FFF , 240px 661px #FFF , 1057px 1144px #FFF , 771px 1910px #FFF , 264px 1977px #FFF , 1944px 1342px #FFF , 1734px 1769px #FFF , 564px 1816px #FFF , 397px 817px #FFF , 786px 463px #FFF , 826px 1772px #FFF , 1487px 780px #FFF , 539px 962px #FFF , 1512px 972px #FFF , 486px 1537px #FFF , 1354px 1027px #FFF , 1466px 368px #FFF , 747px 986px #FFF , 460px 1819px #FFF , 172px 1176px #FFF , 1024px 1824px #FFF , 626px 844px #FFF , 614px 760px #FFF , 1516px 1860px #FFF , 1053px 1294px #FFF , 577px 1953px #FFF , 1809px 171px #FFF , 1068px 1440px #FFF , 1973px 1094px #FFF , 695px 1002px #FFF , 1921px 1510px #FFF , 1045px 1620px #FFF , 92px 991px #FFF , 1121px 1656px #FFF , 144px 386px #FFF , 829px 727px #FFF , 292px 1620px #FFF , 580px 1310px #FFF , 565px 1490px #FFF , 748px 438px #FFF , 1832px 1495px #FFF , 1666px 1201px #FFF , 300px 899px #FFF , 1976px 1478px #FFF , 163px 413px #FFF , 845px 1699px #FFF , 9px 1124px #FFF , 289px 1753px #FFF , 1482px 942px #FFF , 1184px 1629px #FFF , 1638px 302px #FFF , 668px 1007px #FFF , 1037px 1135px #FFF , 912px 72px #FFF , 1493px 968px #FFF , 446px 397px #FFF , 1921px 1172px #FFF , 1404px 876px #FFF , 497px 1317px #FFF , 1725px 1373px #FFF , 302px 948px #FFF , 1850px 294px #FFF , 276px 806px #FFF , 512px 570px #FFF , 811px 314px #FFF , 903px 123px #FFF , 359px 1803px #FFF , 968px 1609px #FFF , 621px 1102px #FFF , 304px 1915px #FFF , 950px 252px #FFF , 434px 1878px #FFF , 161px 762px #FFF , 960px 1279px #FFF , 41px 894px #FFF , 1022px 116px #FFF , 984px 1624px #FFF , 225px 6px #FFF , 832px 1603px #FFF , 1076px 794px #FFF , 1139px 1511px #FFF , 655px 500px #FFF , 969px 734px #FFF , 1799px 117px #FFF , 640px 861px #FFF , 1513px 1467px #FFF , 184px 384px #FFF , 124px 1059px #FFF , 1702px 1600px #FFF , 1997px 679px #FFF , 348px 59px #FFF , 1617px 1295px #FFF , 686px 1625px #FFF , 1682px 881px #FFF , 1449px 1763px #FFF , 470px 1493px #FFF , 1170px 60px #FFF , 999px 165px #FFF , 961px 890px #FFF , 585px 524px #FFF , 1618px 1986px #FFF , 1342px 1661px #FFF , 1683px 561px #FFF , 1830px 352px #FFF , 1059px 314px #FFF , 1916px 1498px #FFF , 122px 820px #FFF , 1619px 751px #FFF , 1442px 378px #FFF , 672px 719px #FFF , 565px 1529px #FFF , 1874px 311px #FFF , 267px 1081px #FFF , 1252px 778px #FFF , 1293px 81px #FFF , 735px 1156px #FFF , 1966px 1421px #FFF , 430px 1990px #FFF , 815px 1593px #FFF , 1399px 1618px #FFF , 369px 1528px #FFF , 817px 287px #FFF , 408px 550px #FFF , 1933px 43px #FFF , 109px 1808px #FFF , 1718px 722px #FFF , 927px 608px #FFF , 1430px 1720px #FFF , 1686px 1311px #FFF , 666px 677px #FFF , 835px 513px #FFF , 1160px 30px #FFF , 1555px 287px #FFF , 1850px 1778px #FFF , 1494px 562px #FFF , 1826px 1535px #FFF , 524px 129px #FFF , 1034px 1464px #FFF , 832px 338px #FFF , 1431px 894px #FFF , 753px 1958px #FFF , 295px 337px #FFF , 934px 1342px #FFF , 971px 1119px #FFF , 61px 591px #FFF , 785px 1970px #FFF , 1105px 381px #FFF , 285px 1361px #FFF , 1229px 330px #FFF , 774px 1614px #FFF , 1801px 414px #FFF , 1950px 203px #FFF , 291px 1329px #FFF , 1489px 1377px #FFF , 1320px 116px #FFF , 635px 772px #FFF , 1929px 1674px #FFF , 1215px 671px #FFF , 180px 1559px #FFF , 376px 633px #FFF , 68px 1016px #FFF , 1699px 1763px #FFF , 1401px 1746px #FFF , 891px 474px #FFF , 1722px 1193px #FFF , 1799px 122px #FFF , 614px 1125px #FFF , 1569px 1107px #FFF , 60px 1055px #FFF , 271px 1385px #FFF , 1253px 542px #FFF , 897px 426px #FFF , 893px 1154px #FFF , 1034px 1686px #FFF , 162px 1625px #FFF , 1955px 1259px #FFF , 382px 76px #FFF , 1940px 1526px #FFF , 117px 1930px #FFF , 634px 1451px #FFF , 303px 91px #FFF , 1642px 771px #FFF , 663px 734px #FFF , 1074px 1829px #FFF , 709px 985px #FFF , 1741px 1117px #FFF , 1154px 1810px #FFF , 45px 732px #FFF , 1243px 1980px #FFF , 411px 1313px #FFF , 621px 1761px #FFF , 363px 1132px #FFF , 1063px 368px #FFF , 1946px 426px #FFF , 1664px 200px #FFF , 1255px 1286px #FFF , 187px 1931px #FFF , 1109px 570px #FFF , 1755px 963px #FFF , 896px 1164px #FFF , 959px 66px #FFF , 1961px 1369px #FFF , 869px 1637px #FFF , 964px 1611px #FFF , 1687px 1926px #FFF , 49px 1400px #FFF , 1773px 237px #FFF , 920px 640px #FFF , 314px 167px #FFF , 199px 1465px #FFF , 788px 1065px #FFF , 1482px 1194px #FFF , 1040px 1331px #FFF , 101px 55px #FFF , 798px 367px #FFF , 164px 1467px #FFF , 1830px 1169px #FFF , 335px 1794px #FFF , 1043px 251px #FFF , 1761px 1868px #FFF , 1082px 1960px #FFF , 1409px 1700px #FFF , 1033px 1125px #FFF , 671px 133px #FFF , 1007px 952px #FFF , 381px 433px #FFF , 761px 134px #FFF , 341px 1672px #FFF , 1952px 353px #FFF , 295px 1246px #FFF , 380px 241px #FFF , 978px 506px #FFF , 474px 1011px #FFF , 1608px 1574px #FFF , 595px 1826px #FFF , 382px 2px #FFF , 1277px 1887px #FFF , 1163px 1831px #FFF , 728px 1272px #FFF , 221px 1093px #FFF , 71px 1036px #FFF , 1694px 273px #FFF , 1507px 380px #FFF , 1544px 1605px #FFF , 50px 1320px #FFF , 381px 1018px #FFF , 909px 1878px #FFF , 1606px 1987px #FFF , 1513px 1933px #FFF , 1728px 31px #FFF , 347px 206px #FFF , 1548px 1400px #FFF , 1114px 102px #FFF , 1973px 740px #FFF , 623px 205px #FFF , 1264px 488px #FFF , 315px 78px #FFF , 398px 1689px #FFF , 303px 566px #FFF , 985px 256px #FFF , 891px 858px #FFF , 424px 799px #FFF , 472px 1928px #FFF , 1702px 1252px #FFF , 1704px 1273px #FFF , 1208px 1282px #FFF , 594px 127px #FFF , 119px 741px #FFF , 1893px 1374px #FFF , 752px 1322px #FFF , 1818px 1140px #FFF , 121px 419px #FFF , 1297px 1412px #FFF , 352px 1385px #FFF , 1444px 159px #FFF , 433px 317px #FFF , 639px 1231px #FFF , 971px 1526px #FFF , 1343px 1504px #FFF , 687px 119px #FFF , 1088px 1274px #FFF , 964px 1680px #FFF , 777px 815px #FFF , 119px 775px #FFF , 1260px 547px #FFF , 1914px 943px #FFF , 320px 930px #FFF , 898px 117px #FFF , 951px 1543px #FFF , 638px 40px #FFF , 97px 1918px #FFF , 1573px 205px #FFF , 442px 549px #FFF , 291px 1359px #FFF , 421px 598px #FFF , 500px 573px #FFF , 1530px 179px #FFF , 63px 107px #FFF , 442px 839px #FFF , 1974px 1827px #FFF , 116px 93px #FFF , 1858px 1272px #FFF , 666px 1959px #FFF , 140px 1386px #FFF , 1614px 1062px #FFF , 258px 832px #FFF , 873px 318px #FFF , 88px 1043px #FFF , 799px 614px #FFF , 418px 725px #FFF , 1647px 1019px #FFF , 316px 1525px #FFF , 1352px 402px #FFF , 355px 1983px #FFF , 1922px 1914px #FFF , 858px 1992px #FFF , 295px 1931px #FFF , 1180px 804px #FFF , 1732px 1124px #FFF , 1374px 1108px #FFF , 548px 1406px #FFF , 1173px 1596px #FFF , 891px 892px #FFF , 1672px 573px #FFF , 205px 173px #FFF , 923px 1969px #FFF , 43px 1612px #FFF , 1224px 1057px #FFF , 1623px 543px #FFF , 133px 1274px #FFF , 484px 218px #FFF , 229px 840px #FFF , 1615px 1542px #FFF , 1017px 1436px #FFF , 1289px 1681px #FFF , 631px 348px #FFF , 1818px 315px #FFF , 66px 63px #FFF , 838px 1954px #FFF , 995px 1337px #FFF , 458px 615px #FFF , 39px 190px #FFF , 1943px 103px #FFF , 958px 1157px #FFF , 269px 668px #FFF , 1313px 669px #FFF , 1050px 476px #FFF , 1224px 1184px #FFF , 1946px 958px #FFF , 1929px 713px #FFF , 251px 1302px #FFF , 326px 423px #FFF , 448px 1825px #FFF , 1791px 1376px #FFF , 120px 410px #FFF , 842px 53px #FFF , 1081px 336px #FFF , 720px 1047px #FFF , 109px 855px #FFF , 1413px 487px #FFF , 1602px 1101px #FFF , 1777px 87px #FFF , 1877px 1944px #FFF , 1581px 436px #FFF , 1624px 1735px #FFF , 825px 1097px #FFF , 155px 1153px #FFF , 1966px 1157px #FFF , 72px 169px #FFF , 1075px 672px #FFF , 722px 1788px #FFF , 759px 1610px #FFF , 491px 1308px #FFF , 1025px 167px #FFF , 1174px 4px #FFF , 366px 813px #FFF , 442px 1611px #FFF , 1156px 635px #FFF , 351px 1845px #FFF , 456px 1050px #FFF , 1618px 999px #FFF , 1238px 1425px #FFF , 1667px 1038px #FFF , 767px 1136px #FFF , 1146px 1759px #FFF , 1984px 582px #FFF , 1655px 1165px #FFF , 373px 564px #FFF , 1916px 560px #FFF , 782px 419px #FFF , 1405px 341px #FFF , 1032px 1567px #FFF , 122px 528px #FFF , 1131px 1300px #FFF , 188px 480px #FFF , 1760px 1388px #FFF , 1547px 65px #FFF , 246px 985px #FFF , 1690px 900px #FFF , 1719px 340px #FFF , 815px 61px #FFF , 1748px 380px #FFF , 1783px 62px #FFF , 1489px 1585px #FFF , 906px 233px #FFF , 1649px 1017px #FFF , 1621px 259px #FFF , 400px 1172px #FFF , 416px 1683px #FFF , 1199px 1952px #FFF , 797px 914px #FFF , 832px 1564px #FFF , 1942px 1308px #FFF , 185px 423px #FFF , 54px 854px #FFF , 427px 706px #FFF , 19px 872px #FFF , 1145px 566px #FFF , 1441px 143px #FFF , 858px 1246px #FFF , 1526px 1490px #FFF , 1419px 409px #FFF , 1423px 392px #FFF , 1983px 1729px #FFF , 847px 1674px #FFF , 809px 1864px #FFF , 1798px 1226px #FFF , 512px 82px #FFF , 261px 1694px #FFF , 1514px 807px #FFF , 1046px 577px #FFF , 1229px 802px #FFF , 704px 1702px #FFF , 439px 230px #FFF , 1609px 599px #FFF , 1515px 1810px #FFF , 221px 1192px #FFF , 1530px 989px #FFF , 962px 1273px #FFF , 1470px 881px #FFF , 698px 520px #FFF , 1243px 1013px #FFF , 109px 1356px #FFF , 859px 1516px #FFF , 195px 1558px #FFF , 2000px 899px #FFF , 1106px 816px #FFF , 819px 1913px #FFF , 70px 1386px #FFF , 494px 1296px #FFF , 400px 276px #FFF , 1489px 1970px #FFF , 133px 741px #FFF , 784px 570px #FFF , 1573px 315px #FFF , 495px 132px #FFF , 1725px 1532px #FFF , 744px 1053px #FFF , 806px 1483px #FFF , 1280px 1841px #FFF , 1741px 915px #FFF , 1361px 1574px #FFF , 1223px 1547px #FFF , 1456px 1763px #FFF , 1508px 1635px #FFF , 221px 400px #FFF , 168px 716px #FFF , 91px 1010px #FFF , 1887px 1591px #FFF , 1542px 1256px #FFF , 382px 665px #FFF , 322px 921px #FFF , 1614px 358px #FFF , 1073px 391px #FFF , 905px 235px #FFF , 1893px 1608px #FFF , 1488px 694px #FFF , 1103px 1711px #FFF , 796px 128px #FFF , 357px 971px #FFF , 138px 373px #FFF , 1835px 1128px #FFF , 1746px 195px #FFF , 1678px 74px #FFF , 927px 53px #FFF , 1206px 94px #FFF , 302px 804px #FFF , 922px 1523px #FFF , 504px 1702px #FFF , 1627px 132px #FFF , 282px 528px #FFF , 435px 1529px #FFF , 934px 499px #FFF , 1906px 944px #FFF , 1440px 1829px #FFF , 1271px 1388px #FFF , 840px 1076px #FFF , 1523px 282px #FFF , 543px 891px #FFF , 449px 1720px #FFF , 823px 1448px #FFF , 1849px 274px #FFF , 481px 360px #FFF , 245px 652px #FFF , 256px 691px #FFF , 1612px 152px #FFF , 1878px 175px #FFF , 613px 1634px #FFF , 1151px 1332px #FFF , 24px 1708px #FFF , 424px 1417px #FFF , 855px 1597px #FFF , 261px 706px #FFF , 1679px 1883px #FFF , 1097px 338px #FFF , 1478px 1923px #FFF , 92px 1648px #FFF , 1749px 192px #FFF , 1463px 1621px #FFF , 690px 992px #FFF , 817px 1562px #FFF , 1801px 163px #FFF , 348px 1188px #FFF , 55px 1964px #FFF , 1932px 1524px #FFF , 78px 58px #FFF , 607px 1238px #FFF , 1392px 1624px #FFF , 1282px 1052px #FFF;
  animation: animStar 50s linear infinite;
}
#stars:after {
  content: " ";
  position: absolute;
  top: 2000px;
  width: 1px;
  height: 1px;
  background: transparent;
  box-shadow: 1126px 1224px #FFF , 1459px 542px #FFF , 645px 869px #FFF , 420px 1443px #FFF , 551px 937px #FFF , 295px 87px #FFF , 738px 1543px #FFF , 298px 440px #FFF , 1111px 1213px #FFF , 1906px 1036px #FFF , 828px 363px #FFF , 1359px 1533px #FFF , 870px 1294px #FFF , 465px 274px #FFF , 1854px 387px #FFF , 193px 1815px #FFF , 1051px 1463px #FFF , 1619px 1137px #FFF , 1175px 1592px #FFF , 1252px 1438px #FFF , 130px 1647px #FFF , 1492px 1760px #FFF , 578px 1135px #FFF , 1890px 1562px #FFF , 1077px 398px #FFF , 806px 716px #FFF , 1295px 1099px #FFF , 407px 1567px #FFF , 564px 1189px #FFF , 876px 1876px #FFF , 1143px 1149px #FFF , 1872px 1631px #FFF , 907px 1428px #FFF , 261px 713px #FFF , 456px 537px #FFF , 34px 1056px #FFF , 1517px 175px #FFF , 1363px 1156px #FFF , 1149px 1950px #FFF , 417px 1525px #FFF , 1176px 945px #FFF , 1985px 1454px #FFF , 221px 1046px #FFF , 714px 1739px #FFF , 1562px 584px #FFF , 1520px 415px #FFF , 1699px 1087px #FFF , 810px 1275px #FFF , 600px 1592px #FFF , 1647px 345px #FFF , 1402px 940px #FFF , 401px 1759px #FFF , 1393px 1615px #FFF , 1206px 887px #FFF , 11px 122px #FFF , 1411px 764px #FFF , 23px 1323px #FFF , 1651px 1684px #FFF , 1539px 1147px #FFF , 491px 1216px #FFF , 4px 1886px #FFF , 574px 1324px #FFF , 1688px 1891px #FFF , 844px 1572px #FFF , 1122px 858px #FFF , 689px 750px #FFF , 247px 674px #FFF , 905px 1987px #FFF , 1683px 270px #FFF , 407px 1606px #FFF , 640px 1001px #FFF , 1768px 1175px #FFF , 1439px 1179px #FFF , 1637px 1917px #FFF , 1624px 1321px #FFF , 454px 600px #FFF , 1034px 1473px #FFF , 787px 7px #FFF , 1158px 1417px #FFF , 117px 62px #FFF , 1400px 1079px #FFF , 1382px 309px #FFF , 388px 543px #FFF , 1170px 1204px #FFF , 259px 1908px #FFF , 381px 1846px #FFF , 865px 1448px #FFF , 182px 1999px #FFF , 1051px 1584px #FFF , 1907px 1885px #FFF , 253px 1861px #FFF , 949px 154px #FFF , 1571px 1444px #FFF , 1556px 354px #FFF , 1964px 766px #FFF , 1358px 1515px #FFF , 172px 1267px #FFF , 1641px 101px #FFF , 875px 1872px #FFF , 1233px 1593px #FFF , 1003px 65px #FFF , 1163px 487px #FFF , 1906px 33px #FFF , 308px 952px #FFF , 231px 953px #FFF , 1901px 382px #FFF , 618px 310px #FFF , 1766px 1080px #FFF , 1986px 1545px #FFF , 98px 1459px #FFF , 348px 1110px #FFF , 743px 782px #FFF , 1430px 91px #FFF , 1351px 1716px #FFF , 1624px 1948px #FFF , 1375px 158px #FFF , 130px 1880px #FFF , 908px 856px #FFF , 1967px 227px #FFF , 1757px 420px #FFF , 1986px 981px #FFF , 442px 193px #FFF , 1510px 796px #FFF , 1727px 217px #FFF , 302px 1821px #FFF , 1049px 577px #FFF , 469px 649px #FFF , 537px 285px #FFF , 1253px 1963px #FFF , 1538px 514px #FFF , 1140px 552px #FFF , 1144px 1666px #FFF , 400px 1871px #FFF , 1046px 400px #FFF , 610px 967px #FFF , 1833px 255px #FFF , 937px 925px #FFF , 2px 1851px #FFF , 1012px 1784px #FFF , 1606px 1954px #FFF , 503px 1926px #FFF , 1808px 1122px #FFF , 473px 1083px #FFF , 824px 1715px #FFF , 309px 1825px #FFF , 1758px 1540px #FFF , 670px 497px #FFF , 1254px 605px #FFF , 1821px 1571px #FFF , 619px 408px #FFF , 1951px 1718px #FFF , 1217px 1225px #FFF , 1780px 18px #FFF , 958px 1597px #FFF , 1185px 974px #FFF , 1883px 140px #FFF , 253px 1183px #FFF , 1000px 1633px #FFF , 323px 1568px #FFF , 82px 3px #FFF , 622px 228px #FFF , 21px 1344px #FFF , 911px 33px #FFF , 188px 1781px #FFF , 229px 1524px #FFF , 1489px 713px #FFF , 856px 610px #FFF , 35px 455px #FFF , 1281px 745px #FFF , 1161px 234px #FFF , 767px 1404px #FFF , 907px 561px #FFF , 240px 661px #FFF , 1057px 1144px #FFF , 771px 1910px #FFF , 264px 1977px #FFF , 1944px 1342px #FFF , 1734px 1769px #FFF , 564px 1816px #FFF , 397px 817px #FFF , 786px 463px #FFF , 826px 1772px #FFF , 1487px 780px #FFF , 539px 962px #FFF , 1512px 972px #FFF , 486px 1537px #FFF , 1354px 1027px #FFF , 1466px 368px #FFF , 747px 986px #FFF , 460px 1819px #FFF , 172px 1176px #FFF , 1024px 1824px #FFF , 626px 844px #FFF , 614px 760px #FFF , 1516px 1860px #FFF , 1053px 1294px #FFF , 577px 1953px #FFF , 1809px 171px #FFF , 1068px 1440px #FFF , 1973px 1094px #FFF , 695px 1002px #FFF , 1921px 1510px #FFF , 1045px 1620px #FFF , 92px 991px #FFF , 1121px 1656px #FFF , 144px 386px #FFF , 829px 727px #FFF , 292px 1620px #FFF , 580px 1310px #FFF , 565px 1490px #FFF , 748px 438px #FFF , 1832px 1495px #FFF , 1666px 1201px #FFF , 300px 899px #FFF , 1976px 1478px #FFF , 163px 413px #FFF , 845px 1699px #FFF , 9px 1124px #FFF , 289px 1753px #FFF , 1482px 942px #FFF , 1184px 1629px #FFF , 1638px 302px #FFF , 668px 1007px #FFF , 1037px 1135px #FFF , 912px 72px #FFF , 1493px 968px #FFF , 446px 397px #FFF , 1921px 1172px #FFF , 1404px 876px #FFF , 497px 1317px #FFF , 1725px 1373px #FFF , 302px 948px #FFF , 1850px 294px #FFF , 276px 806px #FFF , 512px 570px #FFF , 811px 314px #FFF , 903px 123px #FFF , 359px 1803px #FFF , 968px 1609px #FFF , 621px 1102px #FFF , 304px 1915px #FFF , 950px 252px #FFF , 434px 1878px #FFF , 161px 762px #FFF , 960px 1279px #FFF , 41px 894px #FFF , 1022px 116px #FFF , 984px 1624px #FFF , 225px 6px #FFF , 832px 1603px #FFF , 1076px 794px #FFF , 1139px 1511px #FFF , 655px 500px #FFF , 969px 734px #FFF , 1799px 117px #FFF , 640px 861px #FFF , 1513px 1467px #FFF , 184px 384px #FFF , 124px 1059px #FFF , 1702px 1600px #FFF , 1997px 679px #FFF , 348px 59px #FFF , 1617px 1295px #FFF , 686px 1625px #FFF , 1682px 881px #FFF , 1449px 1763px #FFF , 470px 1493px #FFF , 1170px 60px #FFF , 999px 165px #FFF , 961px 890px #FFF , 585px 524px #FFF , 1618px 1986px #FFF , 1342px 1661px #FFF , 1683px 561px #FFF , 1830px 352px #FFF , 1059px 314px #FFF , 1916px 1498px #FFF , 122px 820px #FFF , 1619px 751px #FFF , 1442px 378px #FFF , 672px 719px #FFF , 565px 1529px #FFF , 1874px 311px #FFF , 267px 1081px #FFF , 1252px 778px #FFF , 1293px 81px #FFF , 735px 1156px #FFF , 1966px 1421px #FFF , 430px 1990px #FFF , 815px 1593px #FFF , 1399px 1618px #FFF , 369px 1528px #FFF , 817px 287px #FFF , 408px 550px #FFF , 1933px 43px #FFF , 109px 1808px #FFF , 1718px 722px #FFF , 927px 608px #FFF , 1430px 1720px #FFF , 1686px 1311px #FFF , 666px 677px #FFF , 835px 513px #FFF , 1160px 30px #FFF , 1555px 287px #FFF , 1850px 1778px #FFF , 1494px 562px #FFF , 1826px 1535px #FFF , 524px 129px #FFF , 1034px 1464px #FFF , 832px 338px #FFF , 1431px 894px #FFF , 753px 1958px #FFF , 295px 337px #FFF , 934px 1342px #FFF , 971px 1119px #FFF , 61px 591px #FFF , 785px 1970px #FFF , 1105px 381px #FFF , 285px 1361px #FFF , 1229px 330px #FFF , 774px 1614px #FFF , 1801px 414px #FFF , 1950px 203px #FFF , 291px 1329px #FFF , 1489px 1377px #FFF , 1320px 116px #FFF , 635px 772px #FFF , 1929px 1674px #FFF , 1215px 671px #FFF , 180px 1559px #FFF , 376px 633px #FFF , 68px 1016px #FFF , 1699px 1763px #FFF , 1401px 1746px #FFF , 891px 474px #FFF , 1722px 1193px #FFF , 1799px 122px #FFF , 614px 1125px #FFF , 1569px 1107px #FFF , 60px 1055px #FFF , 271px 1385px #FFF , 1253px 542px #FFF , 897px 426px #FFF , 893px 1154px #FFF , 1034px 1686px #FFF , 162px 1625px #FFF , 1955px 1259px #FFF , 382px 76px #FFF , 1940px 1526px #FFF , 117px 1930px #FFF , 634px 1451px #FFF , 303px 91px #FFF , 1642px 771px #FFF , 663px 734px #FFF , 1074px 1829px #FFF , 709px 985px #FFF , 1741px 1117px #FFF , 1154px 1810px #FFF , 45px 732px #FFF , 1243px 1980px #FFF , 411px 1313px #FFF , 621px 1761px #FFF , 363px 1132px #FFF , 1063px 368px #FFF , 1946px 426px #FFF , 1664px 200px #FFF , 1255px 1286px #FFF , 187px 1931px #FFF , 1109px 570px #FFF , 1755px 963px #FFF , 896px 1164px #FFF , 959px 66px #FFF , 1961px 1369px #FFF , 869px 1637px #FFF , 964px 1611px #FFF , 1687px 1926px #FFF , 49px 1400px #FFF , 1773px 237px #FFF , 920px 640px #FFF , 314px 167px #FFF , 199px 1465px #FFF , 788px 1065px #FFF , 1482px 1194px #FFF , 1040px 1331px #FFF , 101px 55px #FFF , 798px 367px #FFF , 164px 1467px #FFF , 1830px 1169px #FFF , 335px 1794px #FFF , 1043px 251px #FFF , 1761px 1868px #FFF , 1082px 1960px #FFF , 1409px 1700px #FFF , 1033px 1125px #FFF , 671px 133px #FFF , 1007px 952px #FFF , 381px 433px #FFF , 761px 134px #FFF , 341px 1672px #FFF , 1952px 353px #FFF , 295px 1246px #FFF , 380px 241px #FFF , 978px 506px #FFF , 474px 1011px #FFF , 1608px 1574px #FFF , 595px 1826px #FFF , 382px 2px #FFF , 1277px 1887px #FFF , 1163px 1831px #FFF , 728px 1272px #FFF , 221px 1093px #FFF , 71px 1036px #FFF , 1694px 273px #FFF , 1507px 380px #FFF , 1544px 1605px #FFF , 50px 1320px #FFF , 381px 1018px #FFF , 909px 1878px #FFF , 1606px 1987px #FFF , 1513px 1933px #FFF , 1728px 31px #FFF , 347px 206px #FFF , 1548px 1400px #FFF , 1114px 102px #FFF , 1973px 740px #FFF , 623px 205px #FFF , 1264px 488px #FFF , 315px 78px #FFF , 398px 1689px #FFF , 303px 566px #FFF , 985px 256px #FFF , 891px 858px #FFF , 424px 799px #FFF , 472px 1928px #FFF , 1702px 1252px #FFF , 1704px 1273px #FFF , 1208px 1282px #FFF , 594px 127px #FFF , 119px 741px #FFF , 1893px 1374px #FFF , 752px 1322px #FFF , 1818px 1140px #FFF , 121px 419px #FFF , 1297px 1412px #FFF , 352px 1385px #FFF , 1444px 159px #FFF , 433px 317px #FFF , 639px 1231px #FFF , 971px 1526px #FFF , 1343px 1504px #FFF , 687px 119px #FFF , 1088px 1274px #FFF , 964px 1680px #FFF , 777px 815px #FFF , 119px 775px #FFF , 1260px 547px #FFF , 1914px 943px #FFF , 320px 930px #FFF , 898px 117px #FFF , 951px 1543px #FFF , 638px 40px #FFF , 97px 1918px #FFF , 1573px 205px #FFF , 442px 549px #FFF , 291px 1359px #FFF , 421px 598px #FFF , 500px 573px #FFF , 1530px 179px #FFF , 63px 107px #FFF , 442px 839px #FFF , 1974px 1827px #FFF , 116px 93px #FFF , 1858px 1272px #FFF , 666px 1959px #FFF , 140px 1386px #FFF , 1614px 1062px #FFF , 258px 832px #FFF , 873px 318px #FFF , 88px 1043px #FFF , 799px 614px #FFF , 418px 725px #FFF , 1647px 1019px #FFF , 316px 1525px #FFF , 1352px 402px #FFF , 355px 1983px #FFF , 1922px 1914px #FFF , 858px 1992px #FFF , 295px 1931px #FFF , 1180px 804px #FFF , 1732px 1124px #FFF , 1374px 1108px #FFF , 548px 1406px #FFF , 1173px 1596px #FFF , 891px 892px #FFF , 1672px 573px #FFF , 205px 173px #FFF , 923px 1969px #FFF , 43px 1612px #FFF , 1224px 1057px #FFF , 1623px 543px #FFF , 133px 1274px #FFF , 484px 218px #FFF , 229px 840px #FFF , 1615px 1542px #FFF , 1017px 1436px #FFF , 1289px 1681px #FFF , 631px 348px #FFF , 1818px 315px #FFF , 66px 63px #FFF , 838px 1954px #FFF , 995px 1337px #FFF , 458px 615px #FFF , 39px 190px #FFF , 1943px 103px #FFF , 958px 1157px #FFF , 269px 668px #FFF , 1313px 669px #FFF , 1050px 476px #FFF , 1224px 1184px #FFF , 1946px 958px #FFF , 1929px 713px #FFF , 251px 1302px #FFF , 326px 423px #FFF , 448px 1825px #FFF , 1791px 1376px #FFF , 120px 410px #FFF , 842px 53px #FFF , 1081px 336px #FFF , 720px 1047px #FFF , 109px 855px #FFF , 1413px 487px #FFF , 1602px 1101px #FFF , 1777px 87px #FFF , 1877px 1944px #FFF , 1581px 436px #FFF , 1624px 1735px #FFF , 825px 1097px #FFF , 155px 1153px #FFF , 1966px 1157px #FFF , 72px 169px #FFF , 1075px 672px #FFF , 722px 1788px #FFF , 759px 1610px #FFF , 491px 1308px #FFF , 1025px 167px #FFF , 1174px 4px #FFF , 366px 813px #FFF , 442px 1611px #FFF , 1156px 635px #FFF , 351px 1845px #FFF , 456px 1050px #FFF , 1618px 999px #FFF , 1238px 1425px #FFF , 1667px 1038px #FFF , 767px 1136px #FFF , 1146px 1759px #FFF , 1984px 582px #FFF , 1655px 1165px #FFF , 373px 564px #FFF , 1916px 560px #FFF , 782px 419px #FFF , 1405px 341px #FFF , 1032px 1567px #FFF , 122px 528px #FFF , 1131px 1300px #FFF , 188px 480px #FFF , 1760px 1388px #FFF , 1547px 65px #FFF , 246px 985px #FFF , 1690px 900px #FFF , 1719px 340px #FFF , 815px 61px #FFF , 1748px 380px #FFF , 1783px 62px #FFF , 1489px 1585px #FFF , 906px 233px #FFF , 1649px 1017px #FFF , 1621px 259px #FFF , 400px 1172px #FFF , 416px 1683px #FFF , 1199px 1952px #FFF , 797px 914px #FFF , 832px 1564px #FFF , 1942px 1308px #FFF , 185px 423px #FFF , 54px 854px #FFF , 427px 706px #FFF , 19px 872px #FFF , 1145px 566px #FFF , 1441px 143px #FFF , 858px 1246px #FFF , 1526px 1490px #FFF , 1419px 409px #FFF , 1423px 392px #FFF , 1983px 1729px #FFF , 847px 1674px #FFF , 809px 1864px #FFF , 1798px 1226px #FFF , 512px 82px #FFF , 261px 1694px #FFF , 1514px 807px #FFF , 1046px 577px #FFF , 1229px 802px #FFF , 704px 1702px #FFF , 439px 230px #FFF , 1609px 599px #FFF , 1515px 1810px #FFF , 221px 1192px #FFF , 1530px 989px #FFF , 962px 1273px #FFF , 1470px 881px #FFF , 698px 520px #FFF , 1243px 1013px #FFF , 109px 1356px #FFF , 859px 1516px #FFF , 195px 1558px #FFF , 2000px 899px #FFF , 1106px 816px #FFF , 819px 1913px #FFF , 70px 1386px #FFF , 494px 1296px #FFF , 400px 276px #FFF , 1489px 1970px #FFF , 133px 741px #FFF , 784px 570px #FFF , 1573px 315px #FFF , 495px 132px #FFF , 1725px 1532px #FFF , 744px 1053px #FFF , 806px 1483px #FFF , 1280px 1841px #FFF , 1741px 915px #FFF , 1361px 1574px #FFF , 1223px 1547px #FFF , 1456px 1763px #FFF , 1508px 1635px #FFF , 221px 400px #FFF , 168px 716px #FFF , 91px 1010px #FFF , 1887px 1591px #FFF , 1542px 1256px #FFF , 382px 665px #FFF , 322px 921px #FFF , 1614px 358px #FFF , 1073px 391px #FFF , 905px 235px #FFF , 1893px 1608px #FFF , 1488px 694px #FFF , 1103px 1711px #FFF , 796px 128px #FFF , 357px 971px #FFF , 138px 373px #FFF , 1835px 1128px #FFF , 1746px 195px #FFF , 1678px 74px #FFF , 927px 53px #FFF , 1206px 94px #FFF , 302px 804px #FFF , 922px 1523px #FFF , 504px 1702px #FFF , 1627px 132px #FFF , 282px 528px #FFF , 435px 1529px #FFF , 934px 499px #FFF , 1906px 944px #FFF , 1440px 1829px #FFF , 1271px 1388px #FFF , 840px 1076px #FFF , 1523px 282px #FFF , 543px 891px #FFF , 449px 1720px #FFF , 823px 1448px #FFF , 1849px 274px #FFF , 481px 360px #FFF , 245px 652px #FFF , 256px 691px #FFF , 1612px 152px #FFF , 1878px 175px #FFF , 613px 1634px #FFF , 1151px 1332px #FFF , 24px 1708px #FFF , 424px 1417px #FFF , 855px 1597px #FFF , 261px 706px #FFF , 1679px 1883px #FFF , 1097px 338px #FFF , 1478px 1923px #FFF , 92px 1648px #FFF , 1749px 192px #FFF , 1463px 1621px #FFF , 690px 992px #FFF , 817px 1562px #FFF , 1801px 163px #FFF , 348px 1188px #FFF , 55px 1964px #FFF , 1932px 1524px #FFF , 78px 58px #FFF , 607px 1238px #FFF , 1392px 1624px #FFF , 1282px 1052px #FFF;
}

#stars2 {
  width: 2px;
  height: 2px;
  background: transparent;
  box-shadow: 1574px 248px #FFF , 984px 1751px #FFF , 1598px 23px #FFF , 558px 1759px #FFF , 867px 1892px #FFF , 905px 596px #FFF , 410px 1032px #FFF , 602px 1475px #FFF , 106px 1659px #FFF , 860px 717px #FFF , 1974px 1535px #FFF , 1562px 1319px #FFF , 1860px 1332px #FFF , 731px 1175px #FFF , 1436px 108px #FFF , 814px 1617px #FFF , 1345px 1525px #FFF , 88px 124px #FFF , 1715px 1698px #FFF , 652px 396px #FFF , 173px 1300px #FFF , 1144px 583px #FFF , 818px 1568px #FFF , 639px 358px #FFF , 1143px 1126px #FFF , 871px 1860px #FFF , 1716px 490px #FFF , 691px 1939px #FFF , 282px 99px #FFF , 204px 364px #FFF , 869px 1661px #FFF , 439px 1297px #FFF , 394px 619px #FFF , 452px 1421px #FFF , 1447px 1189px #FFF , 1223px 1144px #FFF , 1411px 1545px #FFF , 644px 157px #FFF , 25px 1413px #FFF , 717px 1797px #FFF , 1828px 895px #FFF , 620px 974px #FFF , 1087px 643px #FFF , 1292px 562px #FFF , 1352px 962px #FFF , 1784px 704px #FFF , 1601px 912px #FFF , 937px 434px #FFF , 1897px 890px #FFF , 46px 1350px #FFF , 1863px 72px #FFF , 1559px 70px #FFF , 1901px 916px #FFF , 441px 1705px #FFF , 1325px 41px #FFF , 1058px 665px #FFF , 525px 35px #FFF , 1748px 945px #FFF , 850px 756px #FFF , 1274px 1799px #FFF , 1860px 792px #FFF , 1083px 821px #FFF , 1524px 1728px #FFF , 468px 221px #FFF , 1701px 917px #FFF , 1355px 41px #FFF , 499px 1704px #FFF , 149px 1786px #FFF , 1620px 1083px #FFF , 1211px 175px #FFF , 1376px 279px #FFF , 476px 169px #FFF , 1887px 1399px #FFF , 1091px 1676px #FFF , 1326px 633px #FFF , 239px 599px #FFF , 921px 1299px #FFF , 1931px 478px #FFF , 431px 532px #FFF , 488px 612px #FFF , 141px 369px #FFF , 198px 1887px #FFF , 1175px 661px #FFF , 20px 447px #FFF , 61px 663px #FFF , 961px 1909px #FFF , 744px 371px #FFF , 1114px 370px #FFF , 991px 1369px #FFF , 528px 638px #FFF , 301px 306px #FFF , 1092px 898px #FFF , 867px 1389px #FFF , 667px 1247px #FFF , 664px 1856px #FFF , 308px 418px #FFF , 744px 1184px #FFF , 626px 159px #FFF , 1336px 137px #FFF , 1431px 1223px #FFF , 1490px 1974px #FFF , 854px 1681px #FFF , 1981px 1512px #FFF , 1548px 1017px #FFF , 1868px 938px #FFF , 992px 1930px #FFF , 206px 307px #FFF , 588px 659px #FFF , 208px 866px #FFF , 33px 1919px #FFF , 134px 1651px #FFF , 1815px 985px #FFF , 475px 1570px #FFF , 254px 1011px #FFF , 510px 847px #FFF , 813px 1168px #FFF , 1059px 1724px #FFF , 509px 1443px #FFF , 23px 946px #FFF , 996px 300px #FFF , 354px 1414px #FFF , 1902px 1868px #FFF , 314px 1167px #FFF , 139px 1493px #FFF , 370px 1584px #FFF , 162px 402px #FFF , 459px 36px #FFF , 1563px 1243px #FFF , 1316px 1550px #FFF , 969px 1561px #FFF , 1754px 218px #FFF , 1138px 1502px #FFF , 527px 462px #FFF , 1025px 522px #FFF , 694px 1335px #FFF , 1600px 1270px #FFF , 386px 1148px #FFF , 130px 1421px #FFF , 189px 1678px #FFF , 1161px 248px #FFF , 1635px 99px #FFF , 551px 1371px #FFF , 889px 1095px #FFF , 1442px 508px #FFF , 1831px 1454px #FFF , 162px 1861px #FFF , 689px 695px #FFF , 1212px 410px #FFF , 837px 669px #FFF , 356px 316px #FFF , 530px 1524px #FFF , 586px 1650px #FFF , 945px 436px #FFF , 269px 1427px #FFF , 1534px 173px #FFF , 77px 955px #FFF , 462px 439px #FFF , 1137px 1263px #FFF , 843px 1237px #FFF , 54px 1731px #FFF , 1582px 144px #FFF , 151px 866px #FFF , 1955px 1261px #FFF , 1781px 1516px #FFF , 1184px 450px #FFF , 1989px 1090px #FFF , 1002px 276px #FFF , 583px 1778px #FFF , 294px 1340px #FFF , 298px 625px #FFF , 28px 1012px #FFF , 201px 1266px #FFF , 1841px 1960px #FFF , 1876px 477px #FFF , 505px 1011px #FFF , 1939px 1589px #FFF , 471px 573px #FFF , 842px 595px #FFF , 579px 1220px #FFF , 65px 1148px #FFF , 277px 936px #FFF , 771px 1400px #FFF , 1905px 1366px #FFF , 1011px 1722px #FFF , 1155px 497px #FFF , 953px 630px #FFF , 394px 1493px #FFF , 1764px 599px #FFF , 1166px 1020px #FFF , 1120px 514px #FFF , 727px 562px #FFF , 798px 1592px #FFF , 699px 83px #FFF , 152px 1585px #FFF , 1494px 119px #FFF , 990px 1608px #FFF , 1085px 153px #FFF , 1473px 469px #FFF , 604px 42px #FFF , 1665px 1661px #FFF;
  animation: animStar 100s linear infinite;
}
#stars2:after {
  content: " ";
  position: absolute;
  top: 2000px;
  width: 2px;
  height: 2px;
  background: transparent;
  box-shadow: 1574px 248px #FFF , 984px 1751px #FFF , 1598px 23px #FFF , 558px 1759px #FFF , 867px 1892px #FFF , 905px 596px #FFF , 410px 1032px #FFF , 602px 1475px #FFF , 106px 1659px #FFF , 860px 717px #FFF , 1974px 1535px #FFF , 1562px 1319px #FFF , 1860px 1332px #FFF , 731px 1175px #FFF , 1436px 108px #FFF , 814px 1617px #FFF , 1345px 1525px #FFF , 88px 124px #FFF , 1715px 1698px #FFF , 652px 396px #FFF , 173px 1300px #FFF , 1144px 583px #FFF , 818px 1568px #FFF , 639px 358px #FFF , 1143px 1126px #FFF , 871px 1860px #FFF , 1716px 490px #FFF , 691px 1939px #FFF , 282px 99px #FFF , 204px 364px #FFF , 869px 1661px #FFF , 439px 1297px #FFF , 394px 619px #FFF , 452px 1421px #FFF , 1447px 1189px #FFF , 1223px 1144px #FFF , 1411px 1545px #FFF , 644px 157px #FFF , 25px 1413px #FFF , 717px 1797px #FFF , 1828px 895px #FFF , 620px 974px #FFF , 1087px 643px #FFF , 1292px 562px #FFF , 1352px 962px #FFF , 1784px 704px #FFF , 1601px 912px #FFF , 937px 434px #FFF , 1897px 890px #FFF , 46px 1350px #FFF , 1863px 72px #FFF , 1559px 70px #FFF , 1901px 916px #FFF , 441px 1705px #FFF , 1325px 41px #FFF , 1058px 665px #FFF , 525px 35px #FFF , 1748px 945px #FFF , 850px 756px #FFF , 1274px 1799px #FFF , 1860px 792px #FFF , 1083px 821px #FFF , 1524px 1728px #FFF , 468px 221px #FFF , 1701px 917px #FFF , 1355px 41px #FFF , 499px 1704px #FFF , 149px 1786px #FFF , 1620px 1083px #FFF , 1211px 175px #FFF , 1376px 279px #FFF , 476px 169px #FFF , 1887px 1399px #FFF , 1091px 1676px #FFF , 1326px 633px #FFF , 239px 599px #FFF , 921px 1299px #FFF , 1931px 478px #FFF , 431px 532px #FFF , 488px 612px #FFF , 141px 369px #FFF , 198px 1887px #FFF , 1175px 661px #FFF , 20px 447px #FFF , 61px 663px #FFF , 961px 1909px #FFF , 744px 371px #FFF , 1114px 370px #FFF , 991px 1369px #FFF , 528px 638px #FFF , 301px 306px #FFF , 1092px 898px #FFF , 867px 1389px #FFF , 667px 1247px #FFF , 664px 1856px #FFF , 308px 418px #FFF , 744px 1184px #FFF , 626px 159px #FFF , 1336px 137px #FFF , 1431px 1223px #FFF , 1490px 1974px #FFF , 854px 1681px #FFF , 1981px 1512px #FFF , 1548px 1017px #FFF , 1868px 938px #FFF , 992px 1930px #FFF , 206px 307px #FFF , 588px 659px #FFF , 208px 866px #FFF , 33px 1919px #FFF , 134px 1651px #FFF , 1815px 985px #FFF , 475px 1570px #FFF , 254px 1011px #FFF , 510px 847px #FFF , 813px 1168px #FFF , 1059px 1724px #FFF , 509px 1443px #FFF , 23px 946px #FFF , 996px 300px #FFF , 354px 1414px #FFF , 1902px 1868px #FFF , 314px 1167px #FFF , 139px 1493px #FFF , 370px 1584px #FFF , 162px 402px #FFF , 459px 36px #FFF , 1563px 1243px #FFF , 1316px 1550px #FFF , 969px 1561px #FFF , 1754px 218px #FFF , 1138px 1502px #FFF , 527px 462px #FFF , 1025px 522px #FFF , 694px 1335px #FFF , 1600px 1270px #FFF , 386px 1148px #FFF , 130px 1421px #FFF , 189px 1678px #FFF , 1161px 248px #FFF , 1635px 99px #FFF , 551px 1371px #FFF , 889px 1095px #FFF , 1442px 508px #FFF , 1831px 1454px #FFF , 162px 1861px #FFF , 689px 695px #FFF , 1212px 410px #FFF , 837px 669px #FFF , 356px 316px #FFF , 530px 1524px #FFF , 586px 1650px #FFF , 945px 436px #FFF , 269px 1427px #FFF , 1534px 173px #FFF , 77px 955px #FFF , 462px 439px #FFF , 1137px 1263px #FFF , 843px 1237px #FFF , 54px 1731px #FFF , 1582px 144px #FFF , 151px 866px #FFF , 1955px 1261px #FFF , 1781px 1516px #FFF , 1184px 450px #FFF , 1989px 1090px #FFF , 1002px 276px #FFF , 583px 1778px #FFF , 294px 1340px #FFF , 298px 625px #FFF , 28px 1012px #FFF , 201px 1266px #FFF , 1841px 1960px #FFF , 1876px 477px #FFF , 505px 1011px #FFF , 1939px 1589px #FFF , 471px 573px #FFF , 842px 595px #FFF , 579px 1220px #FFF , 65px 1148px #FFF , 277px 936px #FFF , 771px 1400px #FFF , 1905px 1366px #FFF , 1011px 1722px #FFF , 1155px 497px #FFF , 953px 630px #FFF , 394px 1493px #FFF , 1764px 599px #FFF , 1166px 1020px #FFF , 1120px 514px #FFF , 727px 562px #FFF , 798px 1592px #FFF , 699px 83px #FFF , 152px 1585px #FFF , 1494px 119px #FFF , 990px 1608px #FFF , 1085px 153px #FFF , 1473px 469px #FFF , 604px 42px #FFF , 1665px 1661px #FFF;
}

#stars3 {
  width: 3px;
  height: 3px;
  background: transparent;
  box-shadow: 501px 1212px #FFF , 151px 1294px #FFF , 1568px 1056px #FFF , 523px 382px #FFF , 1197px 1923px #FFF , 299px 1399px #FFF , 1972px 739px #FFF , 1657px 1530px #FFF , 1200px 330px #FFF , 1286px 1895px #FFF , 668px 1085px #FFF , 1643px 1270px #FFF , 141px 939px #FFF , 24px 659px #FFF , 1893px 1909px #FFF , 695px 1469px #FFF , 638px 286px #FFF , 428px 1552px #FFF , 818px 864px #FFF , 1519px 1026px #FFF , 395px 1325px #FFF , 1830px 372px #FFF , 703px 66px #FFF , 888px 557px #FFF , 1335px 1034px #FFF , 690px 1615px #FFF , 1902px 1649px #FFF , 23px 1157px #FFF , 535px 776px #FFF , 990px 394px #FFF , 1208px 1048px #FFF , 1126px 939px #FFF , 1570px 1075px #FFF , 315px 82px #FFF , 1549px 1598px #FFF , 755px 484px #FFF , 1311px 1277px #FFF , 808px 1148px #FFF , 694px 195px #FFF , 1525px 345px #FFF , 729px 858px #FFF , 867px 1827px #FFF , 612px 241px #FFF , 1967px 911px #FFF , 971px 1721px #FFF , 1685px 1436px #FFF , 24px 1962px #FFF , 10px 1647px #FFF , 817px 883px #FFF , 690px 741px #FFF , 1866px 1985px #FFF , 1674px 1081px #FFF , 1316px 1941px #FFF , 1800px 1469px #FFF , 1399px 600px #FFF , 1362px 1317px #FFF , 1854px 1692px #FFF , 37px 1628px #FFF , 430px 1359px #FFF , 49px 1329px #FFF , 1855px 623px #FFF , 1914px 368px #FFF , 1189px 1387px #FFF , 198px 213px #FFF , 953px 1218px #FFF , 1028px 1528px #FFF , 1075px 525px #FFF , 1165px 998px #FFF , 1835px 1336px #FFF , 1973px 1382px #FFF , 1504px 1675px #FFF , 1998px 298px #FFF , 1033px 328px #FFF , 334px 927px #FFF , 1446px 440px #FFF , 573px 596px #FFF , 1174px 1750px #FFF , 1154px 1326px #FFF , 918px 1792px #FFF , 1364px 836px #FFF , 1860px 126px #FFF , 1582px 1152px #FFF , 639px 1561px #FFF , 1951px 1283px #FFF , 646px 321px #FFF , 1738px 1031px #FFF , 374px 197px #FFF , 352px 22px #FFF , 164px 1565px #FFF , 270px 455px #FFF , 1600px 840px #FFF , 1126px 1517px #FFF , 1420px 224px #FFF , 398px 51px #FFF , 78px 864px #FFF , 1338px 1871px #FFF , 246px 1224px #FFF , 1422px 1144px #FFF , 583px 592px #FFF , 606px 794px #FFF;
  animation: animStar 150s linear infinite;
}
#stars3:after {
  content: " ";
  position: absolute;
  top: 2000px;
  width: 3px;
  height: 3px;
  background: transparent;
  box-shadow: 501px 1212px #FFF , 151px 1294px #FFF , 1568px 1056px #FFF , 523px 382px #FFF , 1197px 1923px #FFF , 299px 1399px #FFF , 1972px 739px #FFF , 1657px 1530px #FFF , 1200px 330px #FFF , 1286px 1895px #FFF , 668px 1085px #FFF , 1643px 1270px #FFF , 141px 939px #FFF , 24px 659px #FFF , 1893px 1909px #FFF , 695px 1469px #FFF , 638px 286px #FFF , 428px 1552px #FFF , 818px 864px #FFF , 1519px 1026px #FFF , 395px 1325px #FFF , 1830px 372px #FFF , 703px 66px #FFF , 888px 557px #FFF , 1335px 1034px #FFF , 690px 1615px #FFF , 1902px 1649px #FFF , 23px 1157px #FFF , 535px 776px #FFF , 990px 394px #FFF , 1208px 1048px #FFF , 1126px 939px #FFF , 1570px 1075px #FFF , 315px 82px #FFF , 1549px 1598px #FFF , 755px 484px #FFF , 1311px 1277px #FFF , 808px 1148px #FFF , 694px 195px #FFF , 1525px 345px #FFF , 729px 858px #FFF , 867px 1827px #FFF , 612px 241px #FFF , 1967px 911px #FFF , 971px 1721px #FFF , 1685px 1436px #FFF , 24px 1962px #FFF , 10px 1647px #FFF , 817px 883px #FFF , 690px 741px #FFF , 1866px 1985px #FFF , 1674px 1081px #FFF , 1316px 1941px #FFF , 1800px 1469px #FFF , 1399px 600px #FFF , 1362px 1317px #FFF , 1854px 1692px #FFF , 37px 1628px #FFF , 430px 1359px #FFF , 49px 1329px #FFF , 1855px 623px #FFF , 1914px 368px #FFF , 1189px 1387px #FFF , 198px 213px #FFF , 953px 1218px #FFF , 1028px 1528px #FFF , 1075px 525px #FFF , 1165px 998px #FFF , 1835px 1336px #FFF , 1973px 1382px #FFF , 1504px 1675px #FFF , 1998px 298px #FFF , 1033px 328px #FFF , 334px 927px #FFF , 1446px 440px #FFF , 573px 596px #FFF , 1174px 1750px #FFF , 1154px 1326px #FFF , 918px 1792px #FFF , 1364px 836px #FFF , 1860px 126px #FFF , 1582px 1152px #FFF , 639px 1561px #FFF , 1951px 1283px #FFF , 646px 321px #FFF , 1738px 1031px #FFF , 374px 197px #FFF , 352px 22px #FFF , 164px 1565px #FFF , 270px 455px #FFF , 1600px 840px #FFF , 1126px 1517px #FFF , 1420px 224px #FFF , 398px 51px #FFF , 78px 864px #FFF , 1338px 1871px #FFF , 246px 1224px #FFF , 1422px 1144px #FFF , 583px 592px #FFF , 606px 794px #FFF;
}

#title {
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  color: #FFF;
  text-align: center;
  font-family: "lato", sans-serif;
  font-weight: 300;
  font-size: 50px;
  letter-spacing: 10px;
  margin-top: -60px;
  padding-left: 10px;
}
#title span {
  background: -webkit-linear-gradient(white, #38495a);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

@keyframes animStar {
  from {
    transform: translateY(0px);
  }
  to {
    transform: translateY(-2000px);
  }
}

</style>
