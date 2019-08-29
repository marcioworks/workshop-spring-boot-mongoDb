package com.marciosilva.workshopmongo.config;



import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.marciosilva.workshopmongo.domain.Post;
import com.marciosilva.workshopmongo.domain.User;
import com.marciosilva.workshopmongo.dto.AuthorDto;
import com.marciosilva.workshopmongo.dto.CommentDto;
import com.marciosilva.workshopmongo.repository.PostRepository;
import com.marciosilva.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		userRepository.saveAll( Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou Viajar para São Paulo. Abraços!",new AuthorDto(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"),"Bom dia", "Acordei feliz hoje!", new AuthorDto(maria));
	
		CommentDto c1 = new CommentDto("boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDto(alex));
		CommentDto c2 = new CommentDto("Aproveite!", sdf.parse("22/03/2018"), new AuthorDto(bob));
		CommentDto c3 = new CommentDto("tenha um otimo dia!", sdf.parse("23/03/2018"), new AuthorDto(alex));
		
		post1.getComments().addAll(Arrays.asList(c1,c2));
		post2.getComments().addAll(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1,post2  ));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);
	}

}
