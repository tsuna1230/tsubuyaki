package jp.kobe_u.cs.daikibo.tsubuyaki.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.repository.TsubuyakiRepository;

@Service

public class TsubuyakiService {
    @Autowired
    TsubuyakiRepository repo; // レポジトリ
    // つぶやきを投稿
    public Tsubuyaki postTsubuyaki(Tsubuyaki t) {
        //名前がない場合の業務ロジック
        String name = t.getName();
        if (name==null || name.length()==0) {
            t.setName("名無しさん");
        }
        t.setCreatedAt(new Date());  //作成日時をセット
        return repo.save(t); //セーブしたオブジェクトを返却
    }
    // 全つぶやきを新しい順に取得
    public List<Tsubuyaki> getAllTsubuyaki() {
        // 【変更点】IDの降順で全件取得するように変更
        return repo.findAllByOrderByIdDesc();
    }

    //コメントでつぶやきを検索
    public List<Tsubuyaki> searchTsubuyakiByComment(String keyword) {
        return repo.findByCommentContainingOrderByIdDesc(keyword);
    }
    // ユーザー名でつぶやきを検索
    public List<Tsubuyaki> searchTsubuyakiByName(String keyword) {
        return repo.findByNameContainingOrderByIdDesc(keyword);
    }
}
