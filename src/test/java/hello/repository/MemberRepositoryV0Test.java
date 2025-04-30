package hello.repository;

import hello.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void curd() throws SQLException {
        Member member = new Member("memberV1", 10000);

        //save
        repository.save(member);

        //find by id
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", findMember);
    }
}