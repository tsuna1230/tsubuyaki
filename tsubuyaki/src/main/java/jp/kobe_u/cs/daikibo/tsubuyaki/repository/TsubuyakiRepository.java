package jp.kobe_u.cs.daikibo.tsubuyaki.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;

@Repository

public interface TsubuyakiRepository extends CrudRepository<Tsubuyaki, Long>{
    // 全件をIDの降順で取得する
    List<Tsubuyaki> findAllByOrderByIdDesc();
    // コメントにキーワードを含むつぶやきをIDの降順で検索する
    List<Tsubuyaki> findByCommentContainingOrderByIdDesc(String keyword);
    // 名前にキーワードを含むつぶやきをIDの降順で検索する
    List<Tsubuyaki> findByNameContainingOrderByIdDesc(String keyword);

}