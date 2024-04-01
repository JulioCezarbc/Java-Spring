package com.julioCord.todosimple.services;


import com.julioCord.todosimple.models.Task;
import com.julioCord.todosimple.models.User;
import com.julioCord.todosimple.repositories.TaskRepository;
import com.julioCord.todosimple.repositories.UserRepository;
import com.julioCord.todosimple.services.exceptions.DataBindingViolationException;
import com.julioCord.todosimple.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService UserService;


    public Task findById(Long id){
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException("Tarefa não encontrada! " +
                " id: "+ id + ", Tipo: " + Task.class.getName()));
    }

    public List<Task> findAllByUserId(Long userId){
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public Task createTask(Task obj){
        User user = this.UserService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);

        obj = this.taskRepository.save(obj);
        return obj;
    }

    public Task updateTask(Task obj){
        Task newTask = findById(obj.getId());

        newTask.setDescription(obj.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void deleteTask(Long id){
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        }catch (Exception e){
            throw new DataBindingViolationException("Não é possivel excluir, pois há entidades relacionadas! ");
        }
    }




}
