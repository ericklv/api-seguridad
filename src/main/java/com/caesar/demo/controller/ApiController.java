package com.caesar.demo.controller;

import com.caesar.demo.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
@RequestMapping(path = "/api")
public class ApiController {

    @GetMapping(path="/", produces = "application/json")
    public Map<String, String> helloWorld() {
        return new HashMap<String, String>() {{ put("message", "API enable"); }};
    }

    @GetMapping(path="/math/sqrt", produces = "application/json")
    public Map<String, Double> sqrt (@RequestParam("number") String number){
        return new HashMap<String, Double>() {{ put("message", Calculator.sqrt(number)); }};
    }

    // Caesar GET METHOD

    @GetMapping(path="/caesar/decrypt", produces = "application/json")
    public Map<String, String> decrypt(@RequestParam("text") String text,@RequestParam("key") int key)
    {
        return new HashMap<String, String>() {{ put("message",CaesarCipher.decrypt(text, key));}};
    }

    @GetMapping(path="/caesar/encrypt", produces = "application/json")
    public Map<String, String> encrypt(@RequestParam("text") String text, @RequestParam("key") int key)
    {
        return new HashMap<String, String>() {{ put("message",CaesarCipher.encrypt(text, key));}};
    }

    // Caesar POST METHOD

    @PostMapping(path="/caesar/encrypt", produces = "application/json")
    public Map<String, String> encryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.encrypt(text, key));}};
    }

    @PostMapping(path="/caesar/decrypt", produces = "application/json")
    public Map<String, String> decryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.decrypt(text, key));}};
    }

    @PostMapping(path="/vigenere/encrypt", produces = "application/json")
    public Map<String, String> vigenereEncryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        String key = (String) payload.get("key");

        return new HashMap<String, String>() {{ put("message", Vigenere.customEncrypt(alphabet,text, key));}};
    }

    @PostMapping(path="/vigenere/decrypt", produces = "application/json")
    public Map<String, String> vigenereDecryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        String key = (String) payload.get("key");

        return new HashMap<String, String>() {{ put("message",Vigenere.customDecrypt(alphabet,text, key));}};
    }

    @PostMapping(path="/escitala/encrypt", produces = "application/json")
    public Map<String, String> escitalaEncryptPost(@RequestBody Map<String, Object> payload)
    {
        String text = (String) payload.get("text");
        int key = (int) payload.get("sides");
        String order_ = (String) payload.get("order");
        int[] order = Stream.of(order_.split(",")).mapToInt(n ->Integer.parseInt(n)-1).toArray();

        return new HashMap<String, String>() {{ put("message", Scytale.encrypt(text,key,Scytale.checkOrder(key,order)));}};
    }

    @PostMapping(path="/escitala/decrypt", produces = "application/json")
    public Map<String, String> escitalaDecryptPost(@RequestBody Map<String, Object> payload)
    {
        String text = (String) payload.get("text");
        int key = (int) payload.get("sides");
        String order_ = (String) payload.get("order");
        int[] order = Stream.of(order_.split(",")).mapToInt(n ->Integer.parseInt(n)-1).toArray();

        return new HashMap<String, String>() {{ put("message", Scytale.decrypt(text,key,Scytale.checkOrder(key,order)));}};
    }

    @PostMapping(path="/caesar/encrypt/custom", produces = "application/json")
    public Map<String, String> encryptPostCustom(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.customEncrypt(alphabet,text, key));}};
    }

    @PostMapping(path="/caesar/decrypt/custom", produces = "application/json")
    public Map<String, String> decryptPostCustom(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.customDecrypt(alphabet,text, key));}};
    }
}
