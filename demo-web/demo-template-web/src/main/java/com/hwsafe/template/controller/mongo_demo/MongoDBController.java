/*
 * package com.hwsafe.template.controller.mongo_demo;
 * 
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.data.mongodb.core.MongoTemplate; import
 * org.springframework.data.mongodb.core.query.Criteria; import
 * org.springframework.data.mongodb.core.query.Query; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.ResponseBody;
 * 
 * import com.hwsafe.demo.domain.Person; import com.hwsafe.utils.IDGenerator;
 * 
 * //@Controller public class MongoDBController {
 * 
 * //@Autowired private MongoTemplate mongoTemplate;
 * 
 * @RequestMapping("/mongo/person") public String personMongo() { Person
 * person=new Person(); person.setAge(18); person.setName("testMongo");
 * person.setId(IDGenerator.OBJECTID.generate()); mongoTemplate.save(person);
 * return "success"; }
 * 
 * @RequestMapping("/mongo/person/list")
 * 
 * @ResponseBody public List<Person> personMongoList() { Query query=new
 * Query(Criteria.where("name").is("testMongo")); List<Person> list=
 * mongoTemplate.find(query , Person.class); return list;
 * 
 * }
 * 
 * }
 */