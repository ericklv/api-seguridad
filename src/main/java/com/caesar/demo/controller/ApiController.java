package com.caesar.demo.controller;

import com.caesar.demo.service.CaesarCipher;
import com.caesar.demo.service.Calculator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
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

    @PostMapping(path="/caesar/encrypt", produces = "application/json")
    public Map<String, String> encryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.encrypt(text, key));}};
    }

    @PostMapping(path="/caesar/encrypt/custom", produces = "application/json")
    public Map<String, String> encryptPostCustom(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.customEncrypt(alphabet,text, key));}};
    }

    @PostMapping(path="/caesar/decrypt", produces = "application/json")
    public Map<String, String> decryptPost(@RequestBody Map<String, Object> payload)
    {
        String alphabet = (String) payload.get("alphabet");
        String text = (String) payload.get("text");
        int key = (int) payload.get("key");

        return new HashMap<String, String>() {{ put("message",CaesarCipher.decrypt(text, key));}};
    }
}
