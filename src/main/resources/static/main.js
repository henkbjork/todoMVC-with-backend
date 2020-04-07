'use strict';

// The list with all the todoItems
let todoItems = [];

// load from localstorage
document.addEventListener('DOMContentLoaded', getTodos);

// Count items from start in localstorage
document.addEventListener('DOMContentLoaded', itemsLeft);

// Set the checked variable for the "toggle all todo items"
document.addEventListener('DOMContentLoaded', setChecked);
let checked;

// Add a new todo
document.querySelector('#input-text').addEventListener('keypress', addTodo);

// Mark item as done
document.querySelector('#todo-list').addEventListener('click', toggleCheckbox);

// Delete an item
document.querySelector('#todo-list').addEventListener('click', deleteItem);

// All, Active, Completed
document.querySelector('#nav-bar').addEventListener('click', setURL);

// If the url changes
window.addEventListener('popstate', e => {
    displayItems(null, e.state.id);
});

// Set deafult state for the url
history.replaceState({id: null}, 'Default state', '#/');

// Clear completed
document.querySelector('#clear-btn').addEventListener('click', clearCompleted);

// Toggle all todo items
document.querySelector('.fa-angle-down').addEventListener('click', toggleAllItems);

//update item
document.querySelector('#todo-list').addEventListener('dblclick', updateTodo)


function addTodo(event) {
    if(event.keyCode == 13) {
        let textInput = event.target.value.trim();
        if(textInput !== '') {
            const todo = {
            textInput,
            checked: false,
            id: Date.now(),
            };
            todoItems.push(todo);
            storeTodoInLocalStorage(todo);
            event.target.value = '';
            // const listItem = document.querySelector('#todo-list');
            // listItem.insertAdjacentHTML('beforeend', `
            // <li class="todo-item ${todo.checked}" data-key="${todo.id}">
            //     <i class="uncheck checkbox" id="${todo.id}"></i>
            //     <label>${todo.textInput}</label>
            //     <input type="text" class="newInput" value=${todo.textInput} style="display:none"/>
            //     <i class="delete-item"></i>
            // </li>
            // `);
        }
        itemsLeft();
    }
}


function storeTodoInLocalStorage(todo) {
    let todos;
    if(localStorage.getItem('todos') === null) {
        todos = [];
    } else {
        todos = JSON.parse(localStorage.getItem('todos'))
    }
    todos.push(todo);
    localStorage.setItem('todos', JSON.stringify(todos));
}


function getTodos() {
    let todos;
    if(localStorage.getItem('todos') === null) {
        todos = [];
    } else {
        todos = JSON.parse(localStorage.getItem('todos'))
    }

    todos.forEach(todo => {
        if(todo.checked) {
            todoItems.push(todo);
        //     const listItem = document.querySelector('#todo-list');
        //     listItem.insertAdjacentHTML('beforeend', `
        //     <li class="todo-item overline" data-key="${todo.id}">
        //         <i class="check checkbox" id="${todo.id}"></i>
        //         <label>${todo.textInput}</label>
        //         <input type="text" class="newInput" value=${todo.textInput} style="display:none"/>
        //         <i class="delete-item"></i>
        //     </li>
        //     `);
        // } else {
        //     todoItems.push(todo);
        //     const listItem = document.querySelector('#todo-list');
        //     listItem.insertAdjacentHTML('beforeend', `
        //     <li class="todo-item ${todo.checked}" data-key="${todo.id}">
        //         <i class="uncheck checkbox" id="${todo.id}"></i>
        //         <label>${todo.textInput}</label>
        //         <input type="text" class="newInput" value=${todo.textInput} style="display:none"/>
        //         <i class="delete-item"></i>
        //     </li>
        //     `);
        }
    });
}


function setChecked() {
    let checkedTrue = [];
    let checkedFalse = [];
    checkedFalse = todoItems.filter(todo => todo.checked === false);
    checkedTrue = todoItems.filter(todo => todo.checked === true);
    if(checkedFalse.length === todoItems.length) {
        checked = false;
    } else if(checkedTrue.length === todoItems.length) {
        checked = true;
    }
}


function toggleCheckbox(event) {
    if(event.target.classList.contains('checkbox')) {
        const itemKey = event.target.parentElement.dataset.key;  
        toggle(itemKey);
    }
}


function toggle(itemKey) {    
    const itemIndex = todoItems.findIndex(item => item.id === Number(itemKey));
    // toggle the checkbox
    todoItems[itemIndex].checked = !todoItems[itemIndex].checked;
    
    const item = document.querySelector(`[data-key='${itemKey}']`);
    if(todoItems[itemIndex].checked) {
        item.firstElementChild.setAttribute('class', 'check checkbox');
        item.setAttribute('class', 'todo-item overline');
    } else {      
        item.firstElementChild.setAttribute('class', 'uncheck checkbox');
        item.setAttribute('class', 'todo-item false');
    }
    updateLocalStorage();
    itemsLeft();
}


function updateLocalStorage() {
    let todos = JSON.parse(localStorage.getItem('todos'));
    todoItems.forEach((todo, index) => {
        if(todo.checked) {
            todos[index].checked = true;
        } else if(!todo.checked) {
            todos[index].checked = false;
        }
    });
    localStorage.setItem('todos', JSON.stringify(todos));
}


function toggleAllItems() {
    const todos = document.querySelectorAll('#todo-list li i:first-child');
    todos.forEach(todo => {
        let itemId = todo.getAttribute('id');
        let itemIndex = todoItems.findIndex(item => item.id == itemId);
        if(!checked) {
            todoItems[itemIndex].checked = true;
            todo.setAttribute('class', 'check checkbox');
            todo.parentElement.setAttribute('class', 'todo-item overline');
        } else {
            todoItems[itemIndex].checked = false;
            todo.setAttribute('class', 'uncheck checkbox');
            todo.parentElement.setAttribute('class', 'todo-item false');
        }
    });
    updateLocalStorage();
    checked = !checked;
    itemsLeft();
}


function deleteItem(event) {
    if(event.target.classList.contains('delete-item')) {
        const itemKey = event.target.parentElement.dataset.key;
        todoItems = todoItems.filter(item => item.key !== Number(itemKey));
        const todoElement = document.querySelector(`[data-key='${itemKey}']`);
        todoElement.remove();
        todoItems = todoItems.filter(item => item.id != todoElement.dataset.key);
        itemsLeft();
        removeTodoFromLocalStorage(todoElement);
    }
}


function removeTodoFromLocalStorage(todoItem) {
    let todos;
    if(localStorage.getItem('todos') === null) {
        todos = [];
    } else {
        todos = JSON.parse(localStorage.getItem('todos'))
    }

    todos.forEach(function(todo, index) {
        if(todoItem.children[1].textContent === todo.textInput) {
            todos.splice(index, 1);
        }
    });
    localStorage.setItem('todos', JSON.stringify(todos));
}

function clearCompleted() {
    const listItems = document.querySelectorAll('#todo-list li');
    listItems.forEach(item => {
        if(!item.classList.contains('false')) {
            item.remove();
            removeTodoFromLocalStorage(item);
        }
    });
    todoItems = todoItems.filter(item => item.checked === false);
    itemsLeft();
}


// function setURL(event) {
//     let id = event.target.parentElement.id;
//     if(id === 'completed') {
//         history.pushState({id}, `#${id}`, `#/${id}`);
//         displayItems(event, null);
//     } else if(id === 'active') {
//         history.pushState({id}, `#${id}`, `#/${id}`);
//         displayItems(event, null);
//     } else {
//         history.pushState({id}, `#${id}`, `#/${''}`);
//         displayItems(event, null);
//     }
// }


function displayItems(event, event2) {
    const listItems = document.querySelectorAll('#todo-list li');
    if(event !== null) {
        listItems.forEach(item => {
            item.style.display = 'flex';
            if(event.target.parentElement.id === 'completed' && item.classList.contains('false')) {
                item.style.display = 'none';
            } else if(event.target.parentElement.id === 'active' && !item.classList.contains('false')) {
                item.style.display = 'none';
            }
        });
        event.preventDefault();
    } else if(event2 !== null) {
        listItems.forEach(item => {
            item.style.display = 'flex';
            if(event2 === 'completed' && item.classList.contains('false')) {
                item.style.display = 'none';
            } else if(event2 === 'active' && !item.classList.contains('false')) {
                item.style.display = 'none';
            }
        });
    }
}


function itemsLeft() {
    const todos = document.querySelector("#count");
    const numberOfItemsLeft = todoItems.filter(item => item.checked === false);
    todos.textContent = numberOfItemsLeft.length + ' items left';

    //Hide nav-bar and arrow
    if(todoItems.length === 0) {
        document.querySelector('.list-container').style.display = 'none';
        document.querySelector('.fa-angle-down').style.display = 'none';
    } else {
        document.querySelector('.list-container').style.display = 'flex';
        document.querySelector('.fa-angle-down').style.display = 'block';
    }
}


// function updateTodo(event) {
//     if(event.target.parentElement.classList.contains('todo-item')) {
//         let todos;
//         const itemKey = event.target.parentElement.dataset.key;
//         const todoElement = document.querySelector(`[data-key='${itemKey}']`);
//
//         todoElement.children[0].setAttribute('style', 'display:none');
//         todoElement.children[1].setAttribute('style', 'display:none');
//         todoElement.children[2].setAttribute('style', 'display:block');
//         todoElement.children[2].focus();
//
//         todoElement.addEventListener('change', function(event) {
//             if(event.keyCode == 13 || event.type == 'change') {
//                 let textInput = event.target.value;
//                 todoElement.children[1].textContent = textInput;
//                 // update the element
//                 todoElement.children[0].setAttribute('style', 'display:block');
//                 todoElement.children[1].setAttribute('style', 'display:block');
//                 todoElement.children[2].setAttribute('style', 'display:none');
//                 // update the todoList
//                 const itemKey = event.target.parentElement.dataset.key;
//                 const itemIndex = todoItems.findIndex(item => item.id === Number(itemKey));
//                 todoItems.forEach(todo => {
//                     if(todo.id == itemKey) {
//                         todo.textInput = textInput;
//                     }
//                 });
//                 //update localstorage
//                 todos = JSON.parse(localStorage.getItem('todos'));
//                 todos.forEach(todo => {
//                 if(todo.id == itemKey) {
//                     todo.textInput = textInput;
//                 }
//                 });
//                 localStorage.setItem('todos', JSON.stringify(todos));
//             }
//         });
//     }
//}