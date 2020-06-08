# HMAC (Hash-based Message Authentication Code)
전송되는 데이터의 무결성과 인증을 확인하기 위한 방법
* 무결성(integrity) : 메시지가 변경되지 않았다는 성질
* 인증(authentication) : 메시지가 올바른 송신자로부터 온 것이다라는 성질

공유 키를 사용하여 MAC 함수를 통해 생성된 값을 서버로 보내고, 서버에서도 같은 방법으로 MAC 함수를 이용하여 값을 생성하고 같다면 무결성이 입증된다.  
즉, Sender와 Receicer는 **공유키**, **MAC 알고리즘** 을 서로 알고 있는 상태를 전제로 한다.  

* 해시 알고리즘이 그렇게 많지 않기에 해시결과와 원문을 알고 있다면 유추할 수 있기에, 원문에 살짝 양념(salt) 을 섞게 되는데 , 공유키를 사용한다.
    > HMAC = Hash(Hash(message + key) + key)
 
 * 참고 : https://juneyr.dev/2019-06-10/spring-hmac