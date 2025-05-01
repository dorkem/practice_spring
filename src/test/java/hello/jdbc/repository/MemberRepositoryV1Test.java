package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        repository = new MemberRepositoryV1(dataSource);
    }

    @Test
    void curd() throws SQLException {
        Member member = new Member("memberV100", 10000);

        //save
        repository.save(member);

        //find by id
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repository.delete(member.getMemberId());
        assertThatThrownBy(() -> repository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}