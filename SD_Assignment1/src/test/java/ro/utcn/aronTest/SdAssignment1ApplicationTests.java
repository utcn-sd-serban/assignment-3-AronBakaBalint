package ro.utcn.aronTest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import ro.utcn.aron.persistence.memory.InMemoryUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { InMemoryUserRepository.class })
public class SdAssignment1ApplicationTests {

	
}
