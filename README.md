# 원티드 클론코딩



### 개발 프로세스

1. 프로젝트 보드에 계획한 내용을 바탕으로 알맞은 **Milestones를 생성**하고, 해당 목표에 맞게 **알맞은 라벨과 함께 이슈를 생성**한다.

   → 계획: Milestone 단위, 체크박스: Issue 단위로 배정.

2. 개발자는 `issue/<이슈 번호>` 네이밍을 가진 브랜치를 master에서 체크아웃한다. 이슈 번호가 9번이라면, `issue/9`같은 식이다.

3. PR의 작업 단위 마다 커밋을 진행한다. 커밋 메세지는 `[#<이슈 번호>] <커밋 내용>` 네이밍 형태로 한다. 이슈 번호가 9번이라면, `[#9] 유저 기능 구현 완료` 같은 식이다.

4. 작업이 완료되면, **master 브랜치로 pull request**를 올린다. 이후 reviewer 기능을 통해 **동료 개발자에게 리뷰를 요청**하고, **알맞은 라벨을 부여**한다.

5. 상대 개발자는 올려진 **pull request를 리뷰하고, 적절한 라벨을 부여**한다.

6. **리뷰가 approve되면 merge**하고, 문제 없이 작업이 완료되었다면 **issue를 close**하고 **작업 브랜치를 제거**한다.

7. 이슈가 완료되면, 이슈를 Closed 한다.

8. Milestone이 완료되면, Milestone을 Closed 한다.



### 브랜칭 모델

이슈 별로 브랜치를 분기하여 작업.

작업 중, 에러 발생 및 기존 작업을 베이스로한 추가 작업이 필요할 경우, 해당 이슈 브랜치에서 추가 브랜치 생성 후 작업

배포가 가능한 단계까지는 main에 PR 작업.

배포 이후, dev 브랜치를 새로 분기하여 작업.



### 라벨 정의

Done: 개발완료  
Doing: 개발중  
Fast Review: 빠른 리뷰 요청  
In Review: 리뷰중  
Merge Needed: 머지 가능  
Review Needed: 리뷰 필요  
To Do: 개발 예정  

