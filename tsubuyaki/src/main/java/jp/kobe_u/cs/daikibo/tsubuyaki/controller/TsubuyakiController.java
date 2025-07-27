package jp.kobe_u.cs.daikibo.tsubuyaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.service.TsubuyakiService;

@Controller
public class TsubuyakiController {
    @Autowired
    TsubuyakiService ts;
    //タイトル画面を表示
    @GetMapping("/")
    String showIndex() {
        return "index";
    }
    //メイン画面を表示
    @GetMapping("/read")
    String showTsubuyakiList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String searchType, // 検索タイプを受け取る
            Model model) {
        
        List<Tsubuyaki> list;

        if (keyword != null && !keyword.isEmpty()) {
            // searchTypeの値によって呼び出すサービスを切り替え
            if ("name".equals(searchType)) {
                list = ts.searchTsubuyakiByName(keyword);
            } else { // デフォルトは「内容」で検索
                list = ts.searchTsubuyakiByComment(keyword);
            }
            model.addAttribute("keyword", keyword);
            model.addAttribute("searchType", searchType); // 検索タイプを画面に渡す
        } else {
            list = ts.getAllTsubuyaki();
        }
        model.addAttribute("tsubuyakiList", list);   //モデル属性にリストをセット
        model.addAttribute("tsubuyakiForm", new TsubuyakiForm());  //空フォームをセット
        return "tsubuyaki_list"; //リスト画面を返す
    }
    //つぶやきを投稿
    @PostMapping("/read")
    String postTsubuyaki(@ModelAttribute("tsubuyakiForm") TsubuyakiForm form, Model model) {
        //フォームからエンティティに移し替え
        Tsubuyaki t = new Tsubuyaki();
        t.setName(form.getName());
        t.setComment(form.getComment());
        //サービスに投稿処理を依頼
        ts.postTsubuyaki(t);
        return "redirect:/read"; //メイン画面に転送
    }
}

