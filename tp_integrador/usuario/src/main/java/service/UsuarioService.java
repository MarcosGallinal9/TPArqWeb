package service;

import entity.Usuario;

import java.util.List;

@Service
public class UsuarioService {

    UsuarioRepository usuarioRepository;


    public List<Usuario> getAll(){

        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario){
        Usuario nuevoUsuario;
        nuevoUsuario = usuarioRepository.save(usuario);
        return nuevoUsuario;
    }
    public void delete(Usuario usuario){
        usuarioRepository.delete(usuario);
    }

    public Usuario getUserById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario update(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Monopatin> getMonopatines(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Viaje>getViajes(Long userId) {
        return usuarioRepository.findById(id);
    }

//    public  Monopatin saveMonopatin(Long userId, Monopatin monopatin){
//        monopatin.setUserId(userId);
//        Monopatin nuevoMonopatin = carFeignClient.save(monopatin);
//        return carNew;
//    }

//    public Bike saveBike(Long userId, Bike bike){
//        bike.setUserId(userId);
//        Bike bikeNew = bikeFeignClient.save(bike);
//        return bikeNew;
//    }

//    public Map<String, Object> getUsuarioYMonopatines(Long usuarioId){
//        Map<String, Object> result = new HashMap<>();
//        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
//        if(usuario == null){
//            result.put("Mensaje", "No existe el usuario");
//            return result;
//        }
//        result.put("Usuario", usuario);
       // List<Monopatin> monopatines = carFeignClient.getCars(userId);
       // if(mm.isEmpty()){
         //   result.put("Cars","Este usuario no tiene autos");
       // }
//        else
//            result.put("Cars",cars);
//        if(bikes.isEmpty()){
//            result.put("Bikes","Este usuario no tiene motos");
//        }
//        else
//            result.put("Bikes",bikes);
//        return result;
//    }
}
