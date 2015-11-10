import com.pokejava.*;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.Random;

public class PokedexFrame extends JFrame{
	//Sprite es una clase de la libreria pokejava que nos permite obtener el sprite de un pokemon
	private Sprite sprite;
	//Pokemon es la clase que permite crear los objetos Pokemon y asi acceder a los datos del mismo
	private Pokemon pokemon;
	private JList<String> listPokemons;
	private JLabel lblName,lblAtk,lblDef,lblSpAtk,lblSpDef,lblHP,lblSpeed,lblSprite,lblDescription;
	private JPanel mainPanel,pokeInfo;
	private DefaultListModel<String> listModelPokemons;
	private JScrollPane listScrollPane = new JScrollPane();
	
	public PokedexFrame(){
		super("Pokedex");
		mainPanel = new JPanel();
		pokeInfo = new JPanel();
		pokeInfo.setLayout(new BoxLayout(pokeInfo,BoxLayout.Y_AXIS));
		listPokemons = new JList();
		listPokemons.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPokemons.setVisibleRowCount(20);
		listScrollPane.setViewportView(listPokemons);
		//Agrega la informacion sobre el pokemon al panel de informacion
		panelInfoPokemon(pokemon);
		//agrega el listener sobre la lista
		addListenerToList();
		mainPanel.add(listScrollPane);
		mainPanel.add(pokeInfo);
		mainPanel.add(lblDescription);
		//Agrega todos los nombres de los pokemon a el JList
		setModelPokemons();
		setContentPane(mainPanel);
        setSize(800,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	*Obtiene la lista de nombres de los Pokemons y los agrega
	*a la lista
	*/
	private void setModelPokemons(){
		try{
			//Crea una instancia de la clase Pokedex
			Pokedex pokedex = new Pokedex();
			listModelPokemons = new DefaultListModel();
			//Obtenemos la lista de nombres usando el metodo getPokemonsNames()
			for(String p: pokedex.getPokemonsNames()){
				listModelPokemons.addElement(p);
			}
			listPokemons.setModel(listModelPokemons);
		}catch(Exception e){
			System.out.println("Fallo la red");
		}
	}

	public static void main(String args[]){
		new PokedexFrame();
	}

	/**
	*Agrega la informacion sobre un Pokemon al panel de informacion
	*@param pokemon el pokemon del cual se desea mostrar sus datos
	*/
	private void panelInfoPokemon(Pokemon pokemon){
		//si no se tiene una instancia de Pokemon se dejan los campos vacios
		if(pokemon==null){
			
			lblSprite = new JLabel("");
			pokeInfo.add(lblSprite);
			lblName = new JLabel("");
			lblHP = new JLabel("");
			lblAtk = new JLabel("");
			lblDef = new JLabel("");
			lblSpeed = new JLabel("");
			lblSpAtk = new JLabel("");
			lblSpDef = new JLabel("");
			lblDescription = new JLabel("");
			

			pokeInfo.add(new JLabel("Name: "));
			pokeInfo.add(lblName);
			pokeInfo.add(new JLabel("HP: "));
			pokeInfo.add(lblHP);
			pokeInfo.add(new JLabel("Atk: "));
			pokeInfo.add(lblAtk);
			pokeInfo.add(new JLabel("Def: "));
			pokeInfo.add(lblDef);
			pokeInfo.add(new JLabel("Speed: "));
			pokeInfo.add(lblSpeed);
			pokeInfo.add(new JLabel("Sp.Atk: "));
			pokeInfo.add(lblSpAtk);
			pokeInfo.add(new JLabel("Sp.Def: "));
			pokeInfo.add(lblSpDef);
		}
		else{
			//Intenta traer el sprite del Pokemon
			try{
				//Lee la imagen desde la URL
				lblSprite.setIcon(new ImageIcon(ImageIO.read(new URL("http://www.pokeapi.co"+sprite.getImage()))));

			}
			catch(Exception e){
				System.out.println("Error en el request");
			}
			//Agrega la informacion del pokemon a cada campo
			lblName.setText(pokemon.getName());
			lblHP.setText(pokemon.getHP()+"");
			lblAtk.setText(pokemon.getAttack()+"");
			lblDef.setText(pokemon.getDefense()+"");
			lblSpeed.setText(pokemon.getSpeed()+"");
			lblSpAtk.setText(pokemon.getSpAttack()+"");
			lblSpDef.setText(pokemon.getSpDefense()+"");
			lblDescription.setText(getDescription(pokemon));
		}
	}

	/**
	*Agrega el listener a la lista
	*/
	private void addListenerToList(){
		ListSelectionListener listListener = new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				if(!e.getValueIsAdjusting()){
					//crea un pokemon pasandole como parametro el nombre del pokemon seleccionado en la lista
					pokemon = new Pokemon(listPokemons.getSelectedValue().toString());
					//se obtiene el ID del pokemon y se crea el objeto Sprite correspondiente
					sprite = new Sprite(pokemon.getID()+1);
					//crea el panel de informacion con los datos del pokemon
					panelInfoPokemon(pokemon);
				}
			}
		};
		listPokemons.addListSelectionListener(listListener);
	}

	private String getDescription(Pokemon pokemon){
		if(!pokemon.hasDescription()){
			return "";
		}
		ArrayList<Integer> descriptionsId = pokemon.getDescriptionsID();
		int randomDescription = new Random().nextInt(descriptionsId.size());
		return new Description(descriptionsId.get(randomDescription)).getDescription();
	}
}