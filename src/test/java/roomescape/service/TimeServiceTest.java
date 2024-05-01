package roomescape.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;
import roomescape.dto.time.TimeRequest;
import roomescape.global.exception.model.ConflictException;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeRepository;

@JdbcTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    private TimeService timeService;
    private TimeRepository timeRepository;
    private ReservationRepository reservationRepository;

    @BeforeEach
    void init() {
        timeRepository = new TimeRepository(jdbcTemplate, dataSource);
        reservationRepository = new ReservationRepository(jdbcTemplate, dataSource);
        timeService = new TimeService(timeRepository, reservationRepository);
    }

    @Test
    @DisplayName("중복된 예약 시간을 등록하는 경우 예외가 발생한다.")
    void duplicateTimeFail() {
        timeRepository.save(new Time(LocalTime.of(12, 30)));

        assertThatThrownBy(() -> timeService.createTime(new TimeRequest(LocalTime.of(12, 30))))
                .isInstanceOf(ConflictException.class);
    }

    // TODO: given, when, then 분리
    @Test
    @DisplayName("삭제하려는 시간에 예약이 존재하면 예외를 발생한다.")
    void usingTimeDeleteFail() {
        Time time = timeRepository.save(new Time(LocalTime.now()));

        reservationRepository.save(new Reservation("예약", LocalDate.now().plusDays(1L), time));

        assertThatThrownBy(() -> timeService.deleteTime(time.getId()))
                .isInstanceOf(ConflictException.class);
    }

}