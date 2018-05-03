package runOnt;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWL2Datatype;




import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.google.common.collect.Iterables;


public class Main {
	public static String ns = "http://www.semanticweb.org/alex/ontologies/2018/1/untitled-ontology-8#";
	

	public void csv2xml()  throws TransformerException{
	      // "C:/Users/Alex Suciu/Desktop/CSV Convert/csvconv.xslt"
		Source xmlInput = new StreamSource(new File("C:/Users/Alex Suciu/Desktop/CSV Convert/new/csvconv.xslt"));
		Source xsl = new StreamSource(new File("C:/Users/Alex Suciu/Desktop/CSV Convert/new/csvconv.xslt"));
		Result xmlOutput = new StreamResult(new File("C:/Users/Alex Suciu/Desktop/CSV Convert/output99.xml"));

		Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
		transformer.transform(xmlInput, xmlOutput);
	}
	
	public static BufferedReader readCSV(String filePath) throws FileNotFoundException {
		String csvFile = filePath;
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(csvFile));
        return br;
	}
	
    private static String hasComment(OWLClass cls, OWLOntology ontology){
    	try{
    		return Iterables.get(EntitySearcher.getAnnotations(cls.getIRI(), ontology), 0).getValue().toString();
    	}
    	catch(IndexOutOfBoundsException e){
    		return "";
    	}
    }
    
	public static void addData(OWLOntology ont, String data, Boolean selected) throws OWLOntologyStorageException, OWLOntologyCreationException{
		
		BufferedReader buff;
		String line = "";
		String cvsSplitBy = ",";
		String[] resLine = null;
		int counter = 0;
		
		// Open the Ontology
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		
		OWLDataFactory factory = manager.getOWLDataFactory();
        OWLOntology ontology = ont;
		//ontology = manager.loadOntologyFromOntologyDocument(file);

		// Set the URL
        //String ns = "http://www.semanticweb.org/alex/ontologies/2018/1/untitled-ontology-8#";
        
        String[] headers = {"Project","contractNumber","periodDt","mgmtTimePct","techTimePct","integrationTimePct",
                            "industry1Pct","industry2Pct","industry3Pct","industry4Pct","industry5Pct","industry6Pct",
                            "revenueLog","serviceGroupDesc","geoUnitDesc","clientClassificationDesc","publicCompany",
                            "customerStatus","customerAccountGrpDesc","ouDesc","primaryPricingStructure",
                            "contractLength","timePctProgress","revenuePct1","revenuePct2","riskClass",
                            "expectedRevenue","predictedRisk"}; 
        try {
			buff = readCSV(data);
	        while ((line = buff.readLine()) != null) {
	        	if (counter == 0){
	        		counter = 1;
	        		continue;
	        	}
	            resLine = line.split(cvsSplitBy);
	            OWLNamedIndividual project = factory.getOWLNamedIndividual( IRI.create( ns+ resLine[0] ));
	            OWLNamedIndividual risk = factory.getOWLNamedIndividual( IRI.create( ns+ resLine[27]));
	            OWLObjectProperty hasContract = factory.getOWLObjectProperty( IRI.create( ns+"hasContract" ));
	            OWLObjectProperty hasRisk = factory.getOWLObjectProperty( IRI.create( ns+"hasRisk" ));
	            OWLNamedIndividual contract = factory.getOWLNamedIndividual( IRI.create( ns+resLine[1] ));
	    		OWLClass projectCls = factory.getOWLClass(IRI.create(ontology.getOntologyID()
	    		        .getOntologyIRI().toString()
	    		        + "#Project"));
	    		OWLClass contractCls = factory.getOWLClass(IRI.create(ontology.getOntologyID()
	    		        .getOntologyIRI().toString()
	    		        + "#Contract"));
	    		
	    		OWLClassAssertionAxiom axiom0 = factory.getOWLClassAssertionAxiom(projectCls, project);
	    		manager.applyChange(new AddAxiom(ontology, axiom0));
	    		
	    		OWLClassAssertionAxiom axiom1 = factory.getOWLClassAssertionAxiom(contractCls, contract);
	    		manager.applyChange(new AddAxiom(ontology, axiom1));
	    		
	    		OWLAxiom axiom2 = factory.getOWLObjectPropertyAssertionAxiom(hasContract, project,
	                    contract);
	    		manager.applyChange(new AddAxiom(ontology, axiom2));

	            //Project add
	            for(int i=3; i<13; i++){
	            	OWLDataProperty hasDataProperty = factory.getOWLDataProperty( IRI.create( ns+headers[i]));
	            	OWLAxiom axiom10 = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, project, Float.valueOf(resLine[i]));
	                manager.applyChange(new AddAxiom(ontology, axiom10));

	            }

	            for(int i=22; i<25; i++){
	            	OWLDataProperty hasDataProperty = factory.getOWLDataProperty( IRI.create( ns+headers[i]));
	            	OWLAxiom axiom11 = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, project, Float.valueOf(resLine[i]));
	                manager.applyChange(new AddAxiom(ontology, axiom11));
	            }
	            
	            //Contract add
	            for(int i=13; i<16; i++){
	            	/*
	            	OWLDataProperty hasDataProperty = factory.getOWLDataProperty( IRI.create( ns+headers[i]));
	            	OWLAxiom axiom12 = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, contract, resLine[i]);
	                manager.applyChange(new AddAxiom(ontology, axiom12));
	               */
	            	
	                OWLObjectProperty hasObjProperty = factory.getOWLObjectProperty( IRI.create( ns+headers[i]));
	                OWLNamedIndividual individual = factory.getOWLNamedIndividual( IRI.create( ns+ resLine[i]));
	            	OWLAxiom axiom120 = factory.getOWLObjectPropertyAssertionAxiom(hasObjProperty, contract,
	            			individual);
		    		manager.applyChange(new AddAxiom(ontology, axiom120));
	                 
	            }
	            for(int i=18; i<21; i++){
	            	/*
	            	OWLDataProperty hasDataProperty = factory.getOWLDataProperty( IRI.create( ns+headers[i]));
	            	OWLAxiom axiom12 = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, contract, resLine[i]);
	                manager.applyChange(new AddAxiom(ontology, axiom12));
	                */
	            	
	                OWLObjectProperty hasObjProperty = factory.getOWLObjectProperty( IRI.create( ns+headers[i]));
	                OWLNamedIndividual individual = factory.getOWLNamedIndividual( IRI.create( ns+ resLine[i]));
	            	OWLAxiom axiom121 = factory.getOWLObjectPropertyAssertionAxiom(hasObjProperty, contract,
	            			individual);
		    		manager.applyChange(new AddAxiom(ontology, axiom121));
		    		
	                
	            }
	            for(int i=16; i<18; i++){
	            	
	              	OWLDataProperty hasDataProperty = factory.getOWLDataProperty( IRI.create( ns+headers[i]));
	            	OWLAxiom axiom122 = factory.getOWLDataPropertyAssertionAxiom(hasDataProperty, contract, resLine[i]);
	                manager.applyChange(new AddAxiom(ontology, axiom122));	  
	                             
	            }
	            
	            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(resLine[2]);
	            @SuppressWarnings("deprecation")
				OWLLiteral reportDate = factory.getOWLTypedLiteral(df.format(date) + "T00:00:00", OWL2Datatype.XSD_DATE_TIME);
	            
	            OWLDataProperty periodDt = factory.getOWLDataProperty( IRI.create( ns+"periodDt"));
            	OWLAxiom axiom13 = factory.getOWLDataPropertyAssertionAxiom(periodDt, contract, reportDate);
                manager.applyChange(new AddAxiom(ontology, axiom13));
                
                
                OWLDatatype intDatatype = factory.getIntegerOWLDatatype();
                OWLLiteral twentyOne = factory.getOWLLiteral(resLine[21], intDatatype);
                
                OWLDataProperty contractLength = factory.getOWLDataProperty( IRI.create( ns+"contractLength"));
            	OWLAxiom axiom14 = factory.getOWLDataPropertyAssertionAxiom(contractLength, contract, twentyOne);
                manager.applyChange(new AddAxiom(ontology, axiom14));
                
                OWLDataProperty expectedRevenue = factory.getOWLDataProperty( IRI.create( ns+"expectedRevenue"));
            	OWLAxiom axiom15 = factory.getOWLDataPropertyAssertionAxiom(expectedRevenue, project, Float.valueOf(resLine[26]));
                manager.applyChange(new AddAxiom(ontology, axiom15));
                                
                if (selected){
	                OWLAxiom axiom16 = factory.getOWLObjectPropertyAssertionAxiom(hasRisk, project,
		                    risk);
		    		manager.applyChange(new AddAxiom(ontology, axiom16));
                }else{
                	OWLAxiom axiom16 = factory.getOWLObjectPropertyAssertionAxiom(hasRisk, project,
                			factory.getOWLNamedIndividual( IRI.create( ns+ "blank")));
    	    		manager.applyChange(new AddAxiom(ontology, axiom16));
                }
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		//return ontology;
	}

	public static void doReason(String ontfile, String csvfile, Boolean selected, Boolean save){
	    
	    File file = new File(ontfile);
	    
	    OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
	    OWLDataFactory factory = manager.getOWLDataFactory();
	    OWLOntology ontology = null;
	    try {
	        ontology = manager.loadOntologyFromOntologyDocument(file);
	        addData(ontology, csvfile, selected);
	        //Save to ontology for Protege analysis
	        if(save){
	        	manager.saveOntology(ontology, IRI.create(file.toURI()));
	        }
	    } catch (OWLOntologyCreationException e) {
	        e.printStackTrace();
	    } catch (OWLOntologyStorageException e) {
	        e.printStackTrace();
	    }
	    
	     PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner( ontology, new SimpleConfiguration() );
	    
	     OWLClass riskCls = factory.getOWLClass(IRI.create(ontology.getOntologyID()
	             .getOntologyIRI().toString()
	             + "#ProjectWithRisk"));
	                 
	     String leftAlignFormat = "| %-15s | %-26s | %-26s | %-52s |%n";
	     System.out.format("+-----------------+----------------------------+----------------------------+------------------------------------------------------+%n");
	     System.out.format("| Project         | Tier1 Risk                 | Tier2 Risk                 | Description                                          |%n");
	     System.out.format("+-----------------+----------------------------+----------------------------+------------------------------------------------------+%n");
	     
	     for (OWLClass c : riskCls.getClassesInSignature()) {
	         NodeSet<OWLNamedIndividual> instances = reasoner.getInstances(c, false);
	         
	         for(OWLNamedIndividual i : instances.getFlattened()){
	             //System.out.println(i.getIRI().getFragment());
	             NodeSet<OWLClass> classes = reasoner.getTypes(i, false);
	             for(OWLClass k : classes.getFlattened()){
	                 if(!k.getIRI().getFragment().matches("Thing|Project|ProjectWithNoRisk|ProjectWithRisk|CapabilityRisk|ClientRisk|ContractDealRisk|SolutionRisk")){
	                     Set<OWLClass> cls = reasoner.getSuperClasses(k, true).getFlattened();

	                     System.out.format(
	                             leftAlignFormat,i.getIRI().getFragment(), 
	                             cls.iterator().next().getIRI().getFragment(), 
	                             k.getIRI().getFragment(),
	                             hasComment(k, ontology)
	                             );

	                 }
	                 
	             }
	         }
	     }

	     System.out.format("+-----------------+----------------------------+----------------------------+------------------------------------------------------+%n");


	}
	
	public static void main(String[] args) throws TransformerException, OWLOntologyCreationException, OWLOntologyStorageException {
		//String ontfile = "C:/Users/Alex Suciu/Desktop/CSV Convert/new/THREE_ONTOP.owl";
		//String csvfile = "C:/Users/Alex Suciu/Desktop/CSV Convert/new/importx.csv";
		//System.out.print(doReason(ontfile,csvfile));

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIFrame frame = new UIFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	        
	}
}
	



