/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.prak.postman.pws.c.prak.postman;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ws.Pojo.Product;

/**
 *
 * @author Reza Ramadhani
 */
@RestController
public class ProductServiceController{
    private static Map<String, Product> productRepo = new HashMap<>();
    static {
        Product honey = new Product();
        honey.setId("1");
        honey.setName("Honey");
        honey.setQty("4");
        honey.setPrice("Rp 50000");
        productRepo.put(honey.getId(), honey);
        
        Product almond = new Product();
        almond.setId("2");
        almond.setName("Almond");
        almond.setQty("4");
        almond.setPrice("Rp 40.000");
        productRepo.put(almond.getId(), almond);
    }
    //Method DELETE digunakan apabila kita akan menghapus data yang telah ada dengan menggunakan parameter Id sebagai selectornya
    @RequestMapping(value ="/products/{id}", method = RequestMethod.DELETE)
    public ResponseEntity <Object> delete(@PathVariable("id") String id){
        productRepo.remove(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }
    //Method PUT digunakan apabila kita akan mengubah data yang telah ada dengan menggunakan parameter Id sebagai selectornya
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
         if(!productRepo.containsKey(id)){
           return new ResponseEntity<>("Product key belum ada", HttpStatus.OK);
         }else{
           productRepo.remove(id);
           product.setId(id);
           productRepo.put(id, product);
           return new ResponseEntity<>("Product is updated Successfully", HttpStatus.OK);
       }
   }
   //Method POST digunakan apabila kita akan menambahkan data ke dalam server
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@RequestBody Product product) {
       //membuat fungsi agar id tidak dapat menambahkan data dengan id yang sama
        if (productRepo.containsKey(product.getId())){
           return new ResponseEntity<>("Product key cannot duplicated", HttpStatus.OK);
        }else{
           productRepo.put(product.getId(), product);
           return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
       }
   }

     @RequestMapping(value = "/products")
   public ResponseEntity<Object> getProduct() {
      return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
   }
}