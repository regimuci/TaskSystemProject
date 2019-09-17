package TaskSystemProject.services;

import TaskSystemProject.entities.Comment;
import TaskSystemProject.entities.Task;
import TaskSystemProject.entities.User;
import TaskSystemProject.exceptions.CommentNotExistException;
import TaskSystemProject.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(Task task,User user,Comment comment){
        comment.setTask(task);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId,User user){
        if(!commentRepository.findById(commentId).isPresent()){
            throw new CommentNotExistException("Comment not found");
        }
        if(commentRepository.findById(commentId).get().getUser().getEmail().equals(user.getEmail())){
            commentRepository.deleteById(commentId);
        }

    }

    public List<Comment> findCommentsForTask(Task task){
        return commentRepository.findByTask(task);
    }


}
