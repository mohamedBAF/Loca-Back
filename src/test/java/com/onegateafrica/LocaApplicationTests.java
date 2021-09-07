package com.onegateafrica;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.onegateafrica.entity.AdminOGA;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Agence;
import com.onegateafrica.entity.Categorie;
import com.onegateafrica.entity.Opt;
import com.onegateafrica.entity.PersonelAgence;
import com.onegateafrica.entity.Promotion;
import com.onegateafrica.entity.Vehicule;
import com.onegateafrica.repository.CategorieRepository;
import com.onegateafrica.repository.VehiculeRepository;
import com.onegateafrica.service.CategorieService;
import com.onegateafrica.service.OptionService;
import com.onegateafrica.service.PromotionService;
import com.onegateafrica.service.VehiculeService;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

import org.junit.Test;


@SpringBootTest
public class LocaApplicationTests {


  @Autowired
  VehiculeRepository vehiculeRepository;

  @Autowired
  VehiculeService vehiculeService;

  @Autowired
  CategorieService categorieService;
  @Autowired
  PromotionService promotionService;
  @Autowired
  OptionService optionService;
  @Autowired
  CategorieRepository categorieRepository;


  private List<Vehicule> vehicules;
  private Vehicule vehicule;
  private Agence agence;
  private PersonelAgence personelAgence;
  private AdminOGA adminOGA;
  private Categorie categorie;

  /*@Test
  public void LocaServiceTest() {
     Assert.assertEquals(HttpStatus.OK,
       categorieService.addCategorie(new Categorie(1L, "ariena",new Timestamp(System.currentTimeMillis()),adminOGA,vehicules))
            .getStatusCode());

    Assert.assertEquals(HttpStatus.OK, categorieService.retrieveCategorie(1).getStatusCode());
//    Assert.assertEquals(HttpStatus.OK,categorieService.retrieveCategorie(1).getStatusCode());

  }*/

  /*****test option service******/
  @Test
  public void GetOptTest() {

    Assert.assertEquals(HttpStatus.OK, optionService.getOption(1).getStatusCode());

  }

  @Test
  public void AddOptTest() {
    Assert.assertEquals(HttpStatus.OK,
        optionService.addOption(
            new Opt(1L, "sieege bb","azeaze", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), adminOGA, vehicules))
            .getStatusCode());
  }

  /*****test promotion service******/
  @Test
  public void GetAllPromoTest() {
    Assert.assertEquals(HttpStatus.OK, promotionService.getPromotions());
  }

  @Test
  public void GetPromoTest() {
    Assert.assertEquals(HttpStatus.OK, promotionService.getPromotion(0).getStatusCode());
  }

  @Test
  public void AddPromoTest() {
    Assert.assertEquals(HttpStatus.OK,
        promotionService.addPromotion(
            new Promotion(1L, "aaslema", null, null,
                null, 10, agence, vehicule, personelAgence))
            .getStatusCode());
  }

  /*****test Vehicule service******/
  /*  @Test
  public void AddVehiculeTest() {
     Assert.assertEquals(HttpStatus.OK,
         vehiculeService.addVehiculeByPersonnel(1,
             new Vehicule(1L,"nabeul","Rue","1234","Bk",2018,5,"audi","A4",4,500, "automatique","diesl",50,true,null,5,null,null,null,null,10,6,"desc",agence,null,categorie,null,null))
             .getStatusCode());
   }*/
  @Test
  public void GetVehiculeTest() {
    Assert.assertEquals(HttpStatus.OK, vehiculeService.getVehicule(1).getStatusCode());
  }

  @Test
  public void GetAllVehiculeTest() {
    Assert.assertEquals(HttpStatus.OK, vehiculeService.getAllVehiculesByIdAgence(1));
  }

}
/* //Test avec algo RS256//
  public void testJWTWithRsa() throws NoSuchAlgorithmException {
    KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
    keyGenerator.initialize(1024);

    KeyPair kp = keyGenerator.genKeyPair();
    PublicKey publicKey = (PublicKey) kp.getPublic();
    PrivateKey privateKey = (PrivateKey) kp.getPrivate();

    String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
    System.out.println("Public Key:");
    System.out.println(convertToPublicKey(encodedPublicKey));
    String token = generateJwtToken(privateKey);
    System.out.println("TOKEN:");
    System.out.println(token);
    printStructure(token, publicKey);
  }

  @SuppressWarnings("deprecation")
  public String generateJwtToken(PrivateKey privateKey) {
    String token = Jwts.builder().setSubject("adam")
        .setExpiration(new Date(2018, 1, 1))
        .setIssuer("khouloud@gmail.com")
        .claim("groups", new String[] { "user", "admin" })
        // RS256 with privateKey
        .signWith(SignatureAlgorithm.RS256, privateKey).compact();
    return token;
  }

  //Print structure of JWT
  public void printStructure(String token, PublicKey publicKey) {
    Jws

        parseClaimsJws = Jwts.parser().setSigningKey(publicKey)
        .parseClaimsJws(token);

    System.out.println("Header     : " + parseClaimsJws.getHeader());
    System.out.println("Body       : " + parseClaimsJws.getBody());
    System.out.println("Signature  : " + parseClaimsJws.getSignature());
  }


  // Add BEGIN and END comments
  private String convertToPublicKey(String key){
    StringBuilder result = new StringBuilder();
    result.append("-----BEGIN PUBLIC KEY-----\n");
    result.append(key);
    result.append("\n-----END PUBLIC KEY-----");
    return result.toString();
  }
}

  */












/*

package com.onegateafrica;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;






@SpringBootApplication
public class LocaApplication {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  public static void main(String[] args) {
    SpringApplication.run(com.onegateafrica.LocaApplication.class, args);
  }

}
* */








